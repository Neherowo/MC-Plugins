package pl.nehorowo.rtp.utils;

import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class Util {

    public static void runSync(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static void runAsync(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static Integer getRandomInt(Random ran, int min, int max) {
        return ran.nextInt(max - min) + min;
    }

    public static SlotPos calcSpace(int slot) {
        int row = slot / 9;
        int column = slot - (row * 9);
        return new SlotPos(row, column);
    }
}
