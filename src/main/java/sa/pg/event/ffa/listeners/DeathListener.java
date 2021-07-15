package sa.pg.event.ffa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import sa.pg.event.ffa.Main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeathListener implements Listener {
    private ExecutorService pool = Executors.newCachedThreadPool();
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        event.setDeathMessage(null);
        event.setDroppedExp(0);
        event.getDrops().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(Main.getInstance(), 2L);
        pool.execute(() -> {

        });
    }
}
