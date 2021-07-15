package sa.pg.event.ffa.impl.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import sa.pg.event.ffa.Main;

import java.util.logging.Level;

public class PluginManager {

    public static void registerListeners(Listener... listeners) {
        for(Listener listener : listeners) {
            Main.getInstance().getServer().getPluginManager().registerEvents(listener, Main.getInstance());
        }
    }

    public static void registerCommands(ICommand... commands) {
        for(ICommand command : commands) {
            Main.getInstance().getCommand(command.getName()).setExecutor(command);
            Main.getInstance().getLogger().log(
                    Level.INFO,
                    "Loaded command (/" + command.getName() + ") from class " + command.getClass().getName()
            );
        }
    }

    public static void disable() {
        Bukkit.getPluginManager().disablePlugin(Main.getInstance());
    }

}
