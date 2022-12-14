package saber.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import saber.api.entities.Event;
import saber.api.subscribers.ActiveAnimalSubscriber;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class EventsController
{
    private final Logger log = LoggerFactory.getLogger(EventsController.class);

    public static EventsController instance;

    private final SubmissionPublisher<Event> flowPublisher;

    private final Sinks.Many<Event> sourceFluxPublisher;

    private final Flux<Event> fluxSubscriber;

    private final AtomicInteger eventsTurn = new AtomicInteger(0);

    private EventsController()
    {
        this.flowPublisher = new SubmissionPublisher<>();
        this.sourceFluxPublisher = Sinks.unsafe().many().multicast().directBestEffort();
        this.fluxSubscriber =  sourceFluxPublisher.asFlux().publishOn(Schedulers.parallel()).onBackpressureDrop();
    }

    public static EventsController getInstance()
    {
        if (instance == null)
        {
            synchronized (EventsController.class)
            {
                if (instance == null)
                {
                    instance = new EventsController();
                }
            }
        }
        return instance;
    }

    public void subscribe(ActiveAnimalSubscriber subscriber)
    {
        flowPublisher.subscribe(subscriber);
    }

    public void subscribe(Consumer<? super Event> consumer)
    {
        fluxSubscriber.subscribe(consumer);
    }

    public void submit(Event command)
    {
        command.setEventsTurn(eventsTurn.incrementAndGet());
        log.info("Submitted event with number={}, eventType={}", command.getEventsTurn(), command.getEventType());

        flowPublisher.offer(command, (s, a) ->
        {
            s.onError(new Exception("Can't handle backpressure any more. Dropping value " + a));
            return true;
        });

        sourceFluxPublisher.tryEmitNext(command);
    }

    public void waitALittle()
    {
        try
        {
            Thread.sleep(30_000);
        }
        catch (Exception e)
        {
            // do nothing, meh
        }
    }

}
