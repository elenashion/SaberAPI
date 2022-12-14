package saber.api.entities;

import saber.api.common.EventsController;
import saber.api.subscribers.*;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Owner
{
    private final Set<AnimalSubscriber> animals = ConcurrentHashMap.newKeySet();

    private OwnerMode mode;

    public Owner()
    {
        this.mode = OwnerMode.WORKING;
        animals.add(new CatSubscriber());
        animals.add(new DogSubscriber());
        animals.add(new FishSubscriber());
        animals.add(new SnakeSubscriber());
    }

    public OwnerMode getMode()
    {
        return mode;
    }

    public void setMode(OwnerMode newMode)
    {
        if (newMode == null || newMode.equals(mode))
        {
            return;
        }
        this.mode = newMode;
        EventsController.getInstance().submit(new Event(Event.EventType.MODE_CHANGED, newMode));
    }

    public Set<AnimalSubscriber> getAnimals(AnimalType type)
    {
        if (type == null)
        {
            return Collections.unmodifiableSet(animals);
        }
        return animals.stream().filter(a -> type.equals(a.getType())).collect(Collectors.toSet());
    }

}
