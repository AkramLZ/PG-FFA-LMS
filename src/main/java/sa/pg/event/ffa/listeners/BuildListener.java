package sa.pg.event.ffa.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import sa.pg.event.ffa.Main;

public class BuildListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!Main.getInstance().getBuild().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
