package saber.api.subscribers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saber.api.entities.Event;
import saber.api.entities.AnimalType;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public abstract class ActiveAnimalSubscriber extends AnimalSubscriber implements Subscriber<Event>
{
    private final Logger log = LoggerFactory.getLogger(ActiveAnimalSubscriber.class);

    private Subscription subscription;

    ActiveAnimalSubscriber(AnimalType type)
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
