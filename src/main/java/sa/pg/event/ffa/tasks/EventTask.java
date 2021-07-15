package sa.pg.event.ffa.tasks;

import org.bukkit.Bukkit;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.player.data.DataSlot;
import sa.pg.event.ffa.impl.player.manager.PlayerManager;
import sa.pg.event.ffa.impl.task.FTask;
import sa.pg.event.ffa.impl.task.TaskType;
import sa.pg.event.ffa.manager.EventManager;
import sa.pg.event.ffa.manager.types.EventState;
import sa.pg.event.ffa.utils.board.ScoreboardType;
import sa.pg.event.ffa.utils.board.ScoreboardUtils;

public class EventTask extends FTask {

    public EventTask() {
        super(TaskType.ASYNC);
    }

    @Override
    public Runnable getRunnable() {
        String PREFIX = "§8\u2503 §9§lP§6§lG §8» ";
        return () -> {
            EventManager.setTimeLeft(EventManager.getTimeLeft()-1);
            Bukkit.getOnlinePlayers().forEach(player -> {
                ScoreboardUtils.updateBoard(player, ScoreboardType.EVENT);
            });
            if(EventManager.getTimeLeft() <= 0) {
                stop();
                Bukkit.broadcastMessage(PREFIX + "§eEvent finished, thank you for playing :D");
                EventManager.setEventState(EventState.ENDING);
                Bukkit.getOnlinePlayers().forEach(player -> {
                    for(DataSlot dataSlot : DataSlot.values()) {
                        Main.getInstance().getData().setValue(player, dataSlot, PlayerManager.getPlayer(player).getData().getValue(dataSlot));
                    }
                });
                (EventManager.EVENT_TASK = new EndTask()).start();
            }
        };
    }

    @Override
    public long getDelay() {
        return 0L;
    }

    @Override
    public long getDuration() {
        return 20L;
    }
}
