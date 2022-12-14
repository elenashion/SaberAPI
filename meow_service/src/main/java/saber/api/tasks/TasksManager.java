package saber.api.tasks;

import org.springframework.core.env.Environment;
import saber.api.common.PropertiesUtility;
import saber.api.entities.AnimalType;
import saber.api.entities.Owner;
import saber.api.subscribers.AnimalSubscriber;
import saber.api.subscribers.DogSubscriber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TasksManager
{
    private final Owner owner;

    private final Map<String, AbstractTask> tasksByClassName = new ConcurrentHashMap<>();

    public TasksManager(Environment env)
    {
        this.owner = new Owner();

        int changeModePeriod = PropertiesUtility.getIntegerOrDefault(env.getProperty("tasks.change.mode.period"), 10);
        int changeModeDelay = PropertiesUtility.getIntegerOrDefault(env.getProperty("tasks.change.mode.delay"), 3);

        ChangeModeTask changeModeTask = new ChangeModeTask(
                PropertiesUtility.getBooleanOrDefault(env.getProperty("tasks.change.mode.enabled"), true),
                changeModeDelay, changeModePeriod, this
        );
        tasksByClassName.put(ChangeModeTask.class.getName(), changeModeTask);

        for (AnimalSubscriber subscriber : owner.getAnimals(AnimalType.DOG))
        {
            ((DogSubscriber) subscriber).setOwnerChangeModePeriod(changeModePeriod, changeModeDelay);

            DogsAwooTask dogsAwooTask = new DogsAwooTask(
                    PropertiesUtility.getBooleanOrDefault(env.getProperty("tasks.dogs.awoo.enabled"), true),
                    PropertiesUtility.getIntegerOrDefault(env.getProperty("tasks.dogs.awoo.delay"), 1),
                    PropertiesUtility.getIntegerOrDefault(env.getProperty("tasks.dogs.awoo.period"), 1),
                    (DogSubscriber) subscriber);
            tasksByClassName.put(DogsAwooTask.class.getName() + subscriber.hashCode(), dogsAwooTask);
        }

        SomethingWrongTask somethingWrongTask = new SomethingWrongTask(
                PropertiesUtility.getBooleanOrDefault(env.getProperty("tasks.something.wrong.enabled"), true),
                PropertiesUtility.getIntegerOrDefault(env.getProperty("tasks.something.wrong.delay"), 5),
                PropertiesUtility.getIntegerOrDefault(env.getProperty("tasks.something.wrong.period"), 17)
        );
        tasksByClassName.put(SomethingWrongTask.class.getName(), somethingWrongTask);
    }

    public Owner getOwner()
    {
        return owner;
    }

    public void stopAllTasks()
    {
        for (AbstractTask task : tasksByClassName.values())
        {
            task.stopTask();
        }
    }
}
