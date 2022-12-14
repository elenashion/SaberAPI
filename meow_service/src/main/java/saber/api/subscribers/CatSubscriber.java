package saber.api.subscribers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saber.api.entities.Event;
import saber.api.common.EventsController;
import saber.api.entities.AnimalType;

public class CatSubscriber extends ActiveAnimalSubscriber
{
    private final Logger log = LoggerFactory.getLogger(CatSubscriber.class);

    public CatSubscriber()
    {
        super(AnimalType.CAT);
        EventsController.getInstance().subscribe(this);
    }


    @Override
    protected void applyCommand(Event item)
    {
        EventsController.getInstance().waitALittle();
        log.error("Cat is finished waiting, event number={}", item.getEventsTurn());
    }
}
