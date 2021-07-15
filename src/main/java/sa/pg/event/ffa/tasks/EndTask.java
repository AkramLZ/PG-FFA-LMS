package sa.pg.event.ffa.tasks;

import sa.pg.event.ffa.impl.task.FTask;
import sa.pg.event.ffa.impl.task.TaskType;

public class EndTask extends FTask {
    public EndTask() {
        super(TaskType.ASYNC);
    }

    @Override
    public Runnable getRunnable() {
        return () -> {

        };
    }

    @Override
    public long getDelay() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }
}
