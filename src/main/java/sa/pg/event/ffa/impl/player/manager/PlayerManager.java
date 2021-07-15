package sa.pg.event.ffa.impl.player.manager;

import org.bukkit.entity.Player;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.player.FPlayer;
import sa.pg.event.ffa.impl.player.data.DataSlot;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerManager {

    private static Set<FPlayer> players;
    private static ExecutorService pool;

    static {
        PlayerManager.players = new HashSet<>();
        PlayerManager.pool = Executors.newCachedThreadPool();
    }

    public static FPlayer getPlayer(Player player) {
        return players.stream().filter(fPlayer -> fPlayer.getBukkitPlayer() == player).findFirst().orElse(null);
    }

    public static FPlayer injectPlayer(Player player) {
        if(getPlayer(player) != null) {
            throw new RuntimeException("FPlayer already exists");
        }
        FPlayer fPlayer = new FPlayer(player);
        players.add(fPlayer);
        pool.execute(() -> fPlayer.getData().inject());
        return fPlayer;
    }

    public static FPlayer rejectPlayer(Player player) {
        if(getPlayer(player) == null) {
            throw new RuntimeException("FPlayer does not exists");
        }
        FPlayer fPlayer = getPlayer(player);
        for(DataSlot dataSlot : DataSlot.values()) {
            Main.getInstance().getData().setValue(player, dataSlot, fPlayer.getData().getValue(dataSlot));
        }
        players.remove(fPlayer);
        return fPlayer;
    }

}
