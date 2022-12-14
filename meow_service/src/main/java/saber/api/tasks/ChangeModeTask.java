package saber.api.tasks;

import saber.api.entities.Owner;
import saber.api.entities.OwnerMode;

public class ChangeModeTask extends AbstractTask
{
    private final TasksManager manager;

    public ChangeModeTask(boolean createTask, int timeDelay, int period, TasksManager manager)
    {
        super(createTask, timeDelay, period);
        this.manager = manager;
    }

    @Override
    protected void executeTask()
    {
        synchronized (manager.getOwner())
        {
            Owner owner = manager.getOwner();
            if (owner.getMode().equals(OwnerMode.WORKING))
            {
                owner.setMode(OwnerMode.RESTING);
                log.info("I'm home!");
            }
            else if (owner.getMode().equals(OwnerMode.RESTING))
            {
                owner.setMode(OwnerMode.WORKING);
            }
        }
    }

}
