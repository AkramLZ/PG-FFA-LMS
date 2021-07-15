package sa.pg.event.ffa.impl.region;

import org.bukkit.Location;
import org.bukkit.World;

public class Region {

    private Location min, max;

    public Region(Location pos1, Location pos2) {
        World world = pos1.getWorld();
        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
        this.min = new Location(world, minX, minY, minZ);
        this.max = new Location(world, maxX, maxY, maxZ);
    }

    public boolean isIn(Location location) {
        return location.getBlockX() >= min.getBlockX()
                && location.getBlockX() <= max.getBlockX()
                && location.getBlockY() >= min.getBlockY()
                && location.getBlockY() <= max.getBlockY()
                && location.getBlockZ() >= min.getBlockZ()
                && location.getBlockZ() <= max.getBlockZ();
    }

}
