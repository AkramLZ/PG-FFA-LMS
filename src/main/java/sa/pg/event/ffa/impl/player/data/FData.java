package sa.pg.event.ffa.impl.player.data;

import org.bukkit.entity.Player;
import sa.pg.event.ffa.Main;

import java.util.concurrent.ExecutionException;

public class FData {

    private final Player player;
    private int kills, deaths;

    public FData(Player player) {
        this.player = player;
    }

    public void inject() {
        try {
            this.kills = Main.getInstance().getData().getValue(player, DataSlot.KILLS);
            this.deaths = Main.getInstance().getData().getValue(player, DataSlot.DEATHS);
        } catch (ExecutionException | InterruptedException exception) {
            if(Main.getInstance().isPrintStacktrace()) {
                exception.printStackTrace();
            }
        }
    }

    public int getValue(DataSlot slot) {
        if(slot == null)
            throw new NullPointerException("DataSlot cannot be null");
        if(slot == DataSlot.KILLS) {
            return kills;
        }
        if(slot == DataSlot.DEATHS) {
            return deaths;
        }
        return -404;
    }

}
