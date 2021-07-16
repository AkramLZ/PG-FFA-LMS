package sa.pg.event.ffa.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class DropAndPickupListener implements Listener {
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(!player.isOp() && player.getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }
    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if(!player.isOp() && player.getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }
}