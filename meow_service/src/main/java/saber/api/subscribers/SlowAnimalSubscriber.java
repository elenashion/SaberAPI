package saber.api.subscribers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saber.api.entities.AnimalType;
import saber.api.entities.Event;

public abstract class SlowAnimalSubscriber extends AnimalSubscriber implements Subscriber<Event>
{
    private final Logger log = LoggerFactory.getLogger(SlowAnimalSubscriber.class);

    private Subscription subscription;

    public SlowAnimalSubscriber(AnimalType type)
    {
        super(type);
    }

    @Override
    public void onSubscribe(Subscription subscription)
    {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Event item)
    {
        applyCommand(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable)
    {
        log.error("Something went wrong in [{}], cause={}", this.getClass().getName(), throwable.getMessage());
    }

    @Override
    public void onComplete()
    {
        log.info("Done");
    }
}
