package sa.pg.event.ffa.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import sa.pg.event.ffa.Main;

public class LocationUtils {
    private static Location spawn;

    public static void loadSpawn(FileConfiguration config) {
        if(config.get("spawn") == null)
            return;
        World world = Bukkit.getWorld(config.getString("spawn.world"));
        double x    = config.getDouble("spawn.x");
        double y    = config.getDouble("spawn.y");
        double z    = config.getDouble("spawn.z");
        float yaw   = config.getInt("spawn.yaw");
        float pitch = config.getInt("spawn.pitch");
        spawn       = new Location(world, x, y, z, yaw, pitch);
    }

    public static void saveSpawn(FileConfiguration config, Location location) {
        config.set("spawn.world", location.getWorld().getName());
        config.set("spawn.x", location.getBlockX() + 0.5D);
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getBlockZ() + 0.5D);
        config.set("spawn.yaw", location.getYaw());
        config.set("spawn.pitch", location.getPitch());
        Main.getInstance().saveConfig();
        spawn = location;
    }

    public static void saveLocation(FileConfiguration config, String path, Location location) {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getBlockX() + 0.5D);
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getBlockZ() + 0.5D);
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
        Main.getInstance().saveConfig();
    }

    public static Location getLocation(FileConfiguration configuration, String path) {
        if(configuration.get(path) == null)
            return null;
        World world = Bukkit.getWorld(configuration.getString(path + ".world"));
        double x = configuration.getDouble(path + ".x");
        double y = configuration.getDouble(path + ".y");
        double z = configuration.getDouble(path + ".z");
        float yaw = -404F, pitch = -404F;
        if(configuration.get(path + ".yaw") != null) {
            yaw = configuration.getInt(path + ".yaw");
        }
        if(configuration.get(path + ".pitch") != null) {
            pitch = configuration.getInt(path + ".pitch");
        }
        if(yaw != -404 && pitch != -404) {
            return new Location(world, x, y, z, yaw, pitch);
        } else {
            return new Location(world, x, y, z);
        }
    }

    public static Location getSpawn() {
        return spawn;
    }

}