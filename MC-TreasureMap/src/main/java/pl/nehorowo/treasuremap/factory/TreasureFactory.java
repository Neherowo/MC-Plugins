package pl.nehorowo.treasuremap.factory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.nehorowo.treasuremap.TreasureMapPlugin;
import pl.nehorowo.treasuremap.uti.LocationUtil;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class TreasureFactory {

    private final ConcurrentMap<UUID, Location> treasureMap = new ConcurrentHashMap<>();

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public void startTreasureMap(Player player) {
        if(treasureMap.values().stream().filter(
                uuid -> uuid.equals(player.getUniqueId()))
                .findFirst().orElse(null) == null) {
            TreasureMapPlugin.getInstance().getMessageConfiguration()
                    .getCannotStartTreasure()
                    .send(player);
            return;
        }


        treasureMap.putIfAbsent(player.getUniqueId(), LocationUtil.randomLocation(1000, 1000));



    }
}
