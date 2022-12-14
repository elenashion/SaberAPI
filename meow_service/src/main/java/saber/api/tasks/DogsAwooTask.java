package saber.api.tasks;

import saber.api.subscribers.DogSubscriber;

public class DogsAwooTask extends AbstractTask
{
    private final DogSubscriber subscriber;

    public DogsAwooTask(boolean createTask, int timeDelay, int period, DogSubscriber subscriber)
    {
        super(createTask, timeDelay, period);
        this.subscriber = subscriber;
    }

    @Override
    protected void executeTask()
    {
        if (subscriber.decrementPauseAndGetValue() <= 0)
        {
            log.info("Awooooo!");
        }
    }
}
