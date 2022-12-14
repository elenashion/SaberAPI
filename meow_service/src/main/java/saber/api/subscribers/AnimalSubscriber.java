package saber.api.subscribers;

import saber.api.entities.Event;
import saber.api.entities.AnimalType;

public abstract class AnimalSubscriber
{
    protected AnimalType type;

    protected abstract void applyCommand(Event item);

    public AnimalSubscriber(AnimalType type)
    {
        this.type = type;
    }

    public AnimalType getType()
    {
        return type;
    }

}
