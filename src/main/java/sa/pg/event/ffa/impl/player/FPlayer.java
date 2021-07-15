package sa.pg.event.ffa.impl.player;

import org.bukkit.entity.Player;
import sa.pg.event.ffa.impl.player.data.FData;

public class FPlayer {

    private final FData data;
    private final Player player;

    public FPlayer(Player player) {
        this.player = player;
        this.data = new FData(player);
    }

    public Player getBukkitPlayer() {
        return player;
    }

    public FData getData() {
        return data;
    }

}
