package saber.api.subscribers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saber.api.common.EventsController;
import saber.api.entities.AnimalType;
import saber.api.entities.Event;

public class SnakeSubscriber extends SlowAnimalSubscriber
{
    private final Logger log = LoggerFactory.getLogger(SnakeSubscriber.class);

    public SnakeSubscriber()
    {
        super(AnimalType.SNAKE);
        EventsController.getInstance().subscribe(this::applyCommand);
    }

    @Override
    protected void applyCommand(Event item)
    {
        EventsController.getInstance().waitALittle();
        log.error("Snake is finished waiting, event number={}", item.getEventsTurn());
    }
}
