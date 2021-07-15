package sa.pg.event.ffa.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import sa.pg.event.ffa.manager.EventManager;
import sa.pg.event.ffa.manager.types.EventState;
import sa.pg.event.ffa.utils.LocationUtils;
import sa.pg.event.ffa.utils.RegionUtils;

public class MoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(EventManager.getEventState() != EventState.IN_GAME) {
            if(RegionUtils.getSpawnRegion() != null) {
                Player player = event.getPlayer();
                if(!RegionUtils.getSpawnRegion().isIn(player.getLocation())) {
                    if(LocationUtils.getSpawn() != null) {
                        event.setCancelled(true);
                        player.teleport(LocationUtils.getSpawn());
                        player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1F, 1F);
                    }
                }
            }
        }
    }
}
