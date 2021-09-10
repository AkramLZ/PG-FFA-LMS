package sa.pg.event.ffa;

import sa.pg.event.ffa.commands.BuildCommand;
import sa.pg.event.ffa.commands.EventCommand;
import sa.pg.event.ffa.commands.StatsCommand;
import sa.pg.event.ffa.commands.TopCommand;
import sa.pg.event.ffa.impl.plugin.PluginManager;
import sa.pg.event.ffa.impl.plugin.Provider;
import sa.pg.event.ffa.listeners.*;
import sa.pg.event.ffa.manager.EventManager;
import sa.pg.event.ffa.utils.LocationUtils;
import sa.pg.event.ffa.utils.RegionUtils;

import java.util.logging.Level;

public class MainProvider implements Provider {

    @Override
    public void onEnable() {
        Main.getInstance().getLogger().log(Level.INFO, "[EventManager] Loading listeners...");
        PluginManager.registerListeners(
                new MoveListener(),
                new DeathListener(),
                new DamageListener(),
                new ProjectileListener(),
                new ConnectionListener(),
                new LeavesDecayListener(),
                new PlayerInteractListener(),
                new AchievementAwardedListener(),
                new DropAndPickupListener(),
                new HungerListener(),
                new BuildListener()
        );
        Main.getInstance().getLogger().log(Level.INFO, "[CommandManager] Loading listeners...");
        PluginManager.registerCommands(
                new EventCommand(),
                new TopCommand(),
                new StatsCommand(),
                new BuildCommand()
        );
        RegionUtils.loadSpawnRegion(Main.getInstance().getConfig());
        LocationUtils.loadSpawn(Main.getInstance().getConfig());
        EventManager.setTimeLeft(Main.getInstance().getConfig().getInt("options.event-time"));
    }

}