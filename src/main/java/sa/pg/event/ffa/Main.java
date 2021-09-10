package sa.pg.event.ffa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import sa.pg.event.ffa.impl.plugin.Provider;
import sa.pg.event.ffa.sql.ConnectionResult;
import sa.pg.event.ffa.sql.Data;
import sa.pg.event.ffa.sql.SQLConnection;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main     instance;
    private Provider        provider;
    private SQLConnection   connection;
    private Data            data;
    private boolean         printStacktrace;
    private boolean         clearDatabase;
    private Set<UUID>       build;

    public Main() {
        instance = this;
    }

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        saveDefaultConfig();

        printCopyright();

        this.clearDatabase       = getConfig().getBoolean("dev-section.clear-database-on-start");
        this.connection          = new SQLConnection();
        this.provider            = new MainProvider();
        this.data                = new Data(connection);
        this.build               = new HashSet<>();
        this.printStacktrace     = instance.getConfig().getBoolean("dev-section.print-stacktrace");

        if(clearDatabase) {
            File dataFile        = new File(getDataFolder(), "data.db");
            getLogger().log(Level.INFO, "Delete database enabled, attempting to delete database file...");
            if(dataFile.delete()) {
                getLogger().log(Level.INFO, "Successfully deleted database");
            } else {
                getLogger().log(Level.SEVERE, "Failed to delete database file");
            }
        }

        ConnectionResult result  = connection.connect();

        if(result == ConnectionResult.ALREADY_CONNECTED) {
            getLogger().log(Level.WARNING, "[SQL] The plugin is already connected to database.");
        } else if(result == ConnectionResult.CONNECTION_ERROR) {
            getLogger().log(Level.SEVERE, "[SQL] Failed to connect to database, " + connection.getErrorMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else if(result == ConnectionResult.FILE_ERROR) {
            getLogger().log(Level.SEVERE, "[SQL] Failed to create database file, " + connection.getErrorMessage());
            getServer().getPluginManager().disablePlugin(this);
        } else if(result == ConnectionResult.LIBRARY_ERROR) {
            getLogger().log(Level.SEVERE, "[SQL] Failed to load SQLite library, " + connection.getErrorMessage());
            getServer().getPluginManager().disablePlugin(this);
        } else {
            getLogger().log(Level.INFO, "[SQL] Successfully connected to SQLite database and created connection " + connection.getConnectionID() +
                    " in " + connection.getElapsedTime() + "ms");
        }

        data.createTable();

        provider.onEnable();

        Bukkit.getConsoleSender().sendMessage(
                ChatColor.YELLOW + "[" + getDescription().getName() + "] Successfully loaded plugin in " + (System.currentTimeMillis() - startTime) + "ms"
        );
    }

    @Override
    public void onDisable() {
        provider.onDisable();
        try {
            if(connection.close()) {
                getLogger().log(Level.INFO, "[SQL] Connection " + connection.getConnectionID() + " has been successfully closed.");
            } else {
                getLogger().log(Level.WARNING, "[SQL] Failed to close connection " + connection.getConnectionID() + ", are you sure that it is connected?");
            }
        } catch (SQLException exception) {
            if(printStacktrace) {
                exception.printStackTrace();
            }
        }
    }

    public boolean isPrintStacktrace() {
        return printStacktrace;
    }

    public Provider getProvider() {
        return provider;
    }

    public static Main getInstance() {
        return instance;
    }

    public SQLConnection getConnection() {
        return connection;
    }

    public Data getData() {
        return data;
    }

    public Set<UUID> getBuild() {
        return build;
    }

    protected void printCopyright() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§e[FFA-LMS] §9Pro§6Gamer §fFFA LMS Event");
        Bukkit.getConsoleSender().sendMessage("§c[FFA-LMS] §cAll Rights reversed to WonderCoders @ 2021 ©");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
