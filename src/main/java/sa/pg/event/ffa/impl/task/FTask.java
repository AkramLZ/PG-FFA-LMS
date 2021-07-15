package sa.pg.event.ffa.impl.task;

import org.bukkit.Bukkit;
import sa.pg.event.ffa.Main;

public abstract class FTask {
    private int id;
    private TaskType taskType;

    public FTask(TaskType taskType) {
        this.taskType = taskType;
    }

    public abstract Runnable getRunnable();

    public abstract long getDelay();

    public abstract long getDuration();

    public void start() {
        if(this.taskType == TaskType.SYNC) {
            this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), getRunnable(),
                    getDelay(), getDuration());
        } else if(this.taskType == TaskType.ASYNC) {
            this.id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), getRunnable(),
                    getDelay(), getDuration());
        } else {
            throw new RuntimeException("Something occurred when trying to run FTask");
        }
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(id);
    }

}
