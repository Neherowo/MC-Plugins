package pl.nehorowo.tools.task;

import org.bukkit.Bukkit;

public class WeatherTask implements Runnable {

    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> {
            if (world.hasStorm()) world.setStorm(false);
            world.setTime(6000L);
        });
    }
}
