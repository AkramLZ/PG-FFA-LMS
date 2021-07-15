package sa.pg.event.ffa.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import sa.pg.event.ffa.manager.EventManager;
import sa.pg.event.ffa.manager.types.EventState;
import sa.pg.event.ffa.utils.LocationUtils;
import sa.pg.event.ffa.utils.RegionUtils;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(EventManager.getEventState() != EventState.IN_GAME) {
                event.setCancelled(true);
            }
            if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
            if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                event.setCancelled(true);
                event.getEntity().teleport(LocationUtils.getSpawn());
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if(EventManager.getEventState() != EventState.IN_GAME) {
                event.setCancelled(true);
                return;
            }
            if(RegionUtils.getSpawnRegion() == null)
                return;
            if(RegionUtils.getSpawnRegion().isIn(player.getLocation())
                || RegionUtils.getSpawnRegion().isIn(damager.getLocation())) {
                event.setCancelled(true);
            }
        }
        if(event.getDamager().getType() == EntityType.ARROW) {
            Arrow arrow = (Arrow) event.getDamager();
            if(arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                if(RegionUtils.getSpawnRegion() != null) {
                    if(RegionUtils.getSpawnRegion().isIn(shooter.getLocation())) {
                        event.setCancelled(true);
                    }
                }
            }
            if(event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if(RegionUtils.getSpawnRegion() != null) {
                    if(RegionUtils.getSpawnRegion().isIn(player.getLocation())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
