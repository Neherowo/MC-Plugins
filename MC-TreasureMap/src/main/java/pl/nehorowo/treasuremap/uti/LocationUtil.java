package pl.nehorowo.treasuremap.uti;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class LocationUtil {

    public static Location randomLocation(int x, int z) {
        int finalX = ThreadLocalRandom.current().nextInt(x);
        int finalZ = ThreadLocalRandom.current().nextInt(z);
        System.out.println(finalX + " " + finalZ);
        return new Location(Bukkit.getWorld("world"), finalX, 0, finalZ);
    }
}
