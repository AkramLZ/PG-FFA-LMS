package sa.pg.event.ffa.utils;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.region.Region;

public class RegionUtils {

    private static Region spawnRegion;

    public static void loadSpawnRegion(FileConfiguration configuration) {
        if(configuration.get("region.spawn") == null)
            return;
        Location pos1   = LocationUtils.getLocation(configuration, "region.spawn.pos1");
        Location pos2   = LocationUtils.getLocation(configuration, "region.spawn.pos2");

        assert pos1 != null && pos2 != null;

        spawnRegion     = new Region(pos1, pos2);
    }

    public static void setSpawnRegion(FileConfiguration configuration, Location pos1, Location pos2) {
        configuration.set("region.spawn.pos1.world", pos1.getWorld().getName());
        configuration.set("region.spawn.pos1.x", pos1.getX());
        configuration.set("region.spawn.pos1.y", pos1.getY());
        configuration.set("region.spawn.pos1.z", pos1.getZ());
        configuration.set("region.spawn.pos2.world", pos2.getWorld().getName());
        configuration.set("region.spawn.pos2.x", pos2.getX());
        configuration.set("region.spawn.pos2.y", pos2.getY());
        configuration.set("region.spawn.pos2.z", pos2.getZ());
        Main.getInstance().saveConfig();
        spawnRegion = new Region(pos1, pos2);
    }

    public static Region getSpawnRegion() {
        return spawnRegion;
    }

}
