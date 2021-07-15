package sa.pg.event.ffa.manager;

import org.bukkit.entity.Player;
import sa.pg.event.ffa.impl.task.FTask;
import sa.pg.event.ffa.manager.types.EventState;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static final List<Player> alive = new ArrayList<>();
    private static EventState eventState;
    private static int timeLeft;
    public static FTask EVENT_TASK;

    static {
        EventManager.eventState = EventState.LOBBY;
        EventManager.timeLeft   = 3600;
    }

    public static List<Player> getAlive() {
        return alive;
    }

    public static int getTimeLeft() {
        return timeLeft;
    }

    public static void setTimeLeft(int timeLeft) {
        EventManager.timeLeft = timeLeft;
    }

    public static EventState getEventState() {
        return eventState;
    }

    public static void setEventState(EventState eventState) {
        EventManager.eventState = eventState;
    }
}
