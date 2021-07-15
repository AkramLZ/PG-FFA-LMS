package sa.pg.event.ffa;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sa.pg.event.ffa.impl.plugin.Provider;
import sa.pg.event.ffa.sql.ConnectionResult;
import sa.pg.event.ffa.sql.Data;
import sa.pg.event.ffa.sql.SQLConnection;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main     instance;
    private Provider        provider;
    private SQLConnection   connection;
    private Data            data;
    private boolean         printStacktrace;

    public Main() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        printCopyright();

        this.connection          = new SQLConnection();
        this.provider            = new MainProvider();
        this.data                = new Data(connection);
        this.printStacktrace     = instance.getConfig().getBoolean("dev-section.print-stacktrace");
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
            getLogger().log(Level.INFO, "[SQL] Successfully connected to SQLite database.");
        }

        data.createTable();

        provider.onEnable();
    }

    @Override
    public void onDisable() {
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

    protected void printCopyright() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§e[FFA-LMS] §9Pro§6Gamer §fFFA LMS Event");
        Bukkit.getConsoleSender().sendMessage("§c[FFA-LMS] §cAll Rights reversed to ArDevelopers @ 2021 ©");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
