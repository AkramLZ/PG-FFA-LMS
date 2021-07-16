package sa.pg.event.ffa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.player.data.DataSlot;
import sa.pg.event.ffa.impl.player.data.FData;
import sa.pg.event.ffa.impl.player.manager.PlayerManager;
import sa.pg.event.ffa.utils.KitUtils;
import sa.pg.event.ffa.utils.LocationUtils;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeathListener implements Listener {
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final String PREFIX = "§8\u2503 §9§lP§6§lG §8» ";
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
            FData playerData = PlayerManager.getPlayer(player).getData();
            playerData.setValue(DataSlot.DEATHS, playerData.getValue(DataSlot.DEATHS) + 1);
            if(killer != null) {
                FData killerData = PlayerManager.getPlayer(killer).getData();
                killerData.setValue(DataSlot.KILLS, killerData.getValue(DataSlot.KILLS) + 1);
                String health = new DecimalFormat("##.##").format(killer.getHealth() / 2);
                player.sendMessage(PREFIX + "§cYou got killed by §9" + killer.getName() + " §cwith §4" + health + " ❤ §cleft.");
            } else {
                player.sendMessage(PREFIX + "§cYou have benn died.");
            }
        });
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if(LocationUtils.getSpawn() != null) {
            event.setRespawnLocation(LocationUtils.getSpawn());
        }
        KitUtils.assignKit(event.getPlayer());
    }
}
