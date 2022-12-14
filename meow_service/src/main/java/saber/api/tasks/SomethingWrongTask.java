package saber.api.tasks;

import saber.api.common.EventsController;
import saber.api.entities.Event;

public class SomethingWrongTask extends AbstractTask
{
    public SomethingWrongTask(boolean createTask, int timeDelay, int period)
    {
        super(createTask, timeDelay, period);
    }

    @Override
    protected void executeTask()
    {
        EventsController.getInstance().submit(new Event(Event.EventType.SOMETHING_WRONG));
    }
}
