package saber.api.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTask
{
    protected static final Logger log = LoggerFactory.getLogger(AbstractTask.class.getName());

    private ScheduledFuture<?> task;

    public AbstractTask(boolean createTask, int timeDelay, int period)
    {
        if (createTask)
        {
            createThread(timeDelay, period);
        }
    }

    protected abstract void executeTask();

    private void createThread(int timeDelay, int period)
    {
        try
        {
            this.task = Executors.newSingleThreadScheduledExecutor()
                    .scheduleAtFixedRate((this::executeTask), timeDelay, period, TimeUnit.SECONDS);
        }
        catch (Exception e)
        {
            log.error("Error start task for {}.class cause={}", this.getClass().getName(), e.getCause().toString());
        }
    }

    public void stopTask()
    {
        task.cancel(false);
    }
}
