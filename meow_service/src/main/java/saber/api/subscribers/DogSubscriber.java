package saber.api.subscribers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saber.api.entities.Event;
import saber.api.common.EventsController;
import saber.api.entities.AnimalType;
import saber.api.entities.OwnerMode;

import java.util.concurrent.atomic.AtomicInteger;

public class DogSubscriber extends ActiveAnimalSubscriber
{
    private final Logger log = LoggerFactory.getLogger(DogSubscriber.class);

    private final AtomicInteger pause = new AtomicInteger(5);

    private int maxPatienceWithoutOwner;

    public DogSubscriber()
    {
        super(AnimalType.DOG);
        EventsController.getInstance().subscribe(this);
    }

    @Override
    protected void applyCommand(Event item)
    {
        if (Event.EventType.MODE_CHANGED.equals(item.getEventType()))
        {
            if (OwnerMode.RESTING.equals(item.getNewParameter()))
            {
                pause.set(maxPatienceWithoutOwner);
            }
            else
            {
                log.info("Good luck on work!");
            }
        }
    }

    public int decrementPauseAndGetValue()
    {
        return pause.decrementAndGet();
    }

    public void setOwnerChangeModePeriod(int period, int delay)
    {
        this.maxPatienceWithoutOwner = period + period/2;
        pause.set(maxPatienceWithoutOwner + delay);
    }

}
