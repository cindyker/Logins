package com.minecats.cindyk;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by cindy on 3/21/2015.
 */
public class LoginLocs {
    int x;
    int y;
    int z;
    float yaw;
    float pitch;
    World world;

    public Location getJoinLocation() {
        return new Location(world, x + 0.5, y, z + 0.5, yaw, pitch);
    }
}
