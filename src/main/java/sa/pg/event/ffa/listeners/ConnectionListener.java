package sa.pg.event.ffa.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.player.manager.PlayerManager;
import sa.pg.event.ffa.manager.EventManager;
import sa.pg.event.ffa.manager.types.EventState;
import sa.pg.event.ffa.utils.KitUtils;
import sa.pg.event.ffa.utils.LocationUtils;
import sa.pg.event.ffa.utils.board.ScoreboardType;
import sa.pg.event.ffa.utils.board.ScoreboardUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class ConnectionListener implements Listener {

    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final String PREFIX = "§8\u2503 §9§lP§6§lG §8» ";

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if(EventManager.getEventState() == EventState.ENDING) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, PREFIX + "§cEvent is finished, please try again later.");
            return;
        }
        Player player = event.getPlayer();
        pool.execute(() -> {
            try {
                if(!Main.getInstance().getData().registered(player)) {
                    Main.getInstance().getData().register(player);
                }
            } catch (ExecutionException | InterruptedException e) {
                if(Main.getInstance().isPrintStacktrace()) {
                    e.printStackTrace();
                }
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setAllowFlight(false);
        new BukkitRunnable() {
            @Override public void run() {
                player.sendMessage(PREFIX + "§eWelcome to §6FFA §eEvent, this event hosted by discord.gg/pg");
                if(LocationUtils.getSpawn() != null) {
                    player.teleport(LocationUtils.getSpawn());
                }
            }
        }.runTaskLater(Main.getInstance(), 2L);
        pool.execute(() -> {
            KitUtils.assignKit(player);
            PlayerManager.injectPlayer(player);
            if(EventManager.getEventState() == EventState.LOBBY) {
                ScoreboardUtils.assignScoreboard(player, ScoreboardType.LOBBY);
                Bukkit.getOnlinePlayers().stream().filter(filter -> filter != player).collect(Collectors.toList())
                        .forEach(all -> ScoreboardUtils.updateBoard(all, ScoreboardType.LOBBY));
            }
            if(EventManager.getEventState() == EventState.IN_GAME) {
                ScoreboardUtils.assignScoreboard(player, ScoreboardType.EVENT);
            }
            if(EventManager.getEventState() == EventState.ENDING) {
                ScoreboardUtils.assignScoreboard(player, ScoreboardType.AFTER_EVENT);
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        pool.execute(() -> {
            PlayerManager.rejectPlayer(player);
            ScoreboardUtils.rejectScoreboard(player);
        });
    }
}
