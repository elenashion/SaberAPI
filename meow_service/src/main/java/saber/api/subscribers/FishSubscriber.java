package saber.api.subscribers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saber.api.entities.Event;
import saber.api.common.EventsController;
import saber.api.entities.AnimalType;

public class FishSubscriber extends SlowAnimalSubscriber
{
    private final Logger log = LoggerFactory.getLogger(FishSubscriber.class);

    public FishSubscriber()
    {
        super(AnimalType.FISH);
        EventsController.getInstance().subscribe(this::applyCommand);
    }

    @Override
    protected void applyCommand(Event item)
    {
        if (Event.EventType.MODE_CHANGED.equals(item.getEventType()))
        {
            log.info("Blob");
        }
    }
}
