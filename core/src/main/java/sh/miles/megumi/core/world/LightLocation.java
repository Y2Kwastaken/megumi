package sh.miles.megumi.core.world;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import lombok.Data;
import lombok.NonNull;

@Data
public class LightLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String worldName;
    private final double x;
    private final double y;
    private final double z;

    public static LightLocation fromLocation(@NonNull final Location location) {
        return new LightLocation(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }

    public Location toLocation() {
        final World world = Bukkit.getWorld(this.worldName);
        if (world == null) {
            throw new WorldNotFoundException(this.worldName);
        }
        return new Location(world, x, y, z);
    }

    protected static final class WorldNotFoundException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;

        public WorldNotFoundException(String worldName) {
            super("Error while validating world name. World " + worldName + " does not exist on this server");
        }
    }

    @Override
    public String toString() {
        return "LightLocation{worldName=" + worldName + ", x=" + x + ", y=" + y + ", z=" + z + "}";
    }

}
