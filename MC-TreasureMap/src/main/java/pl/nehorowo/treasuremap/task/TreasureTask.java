package pl.nehorowo.treasuremap.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.nehorowo.treasuremap.TreasureMapPlugin;

public record TreasureTask(TreasureMapPlugin plugin) implements Runnable{
//
    @Override
    public void run() {
//        plugin.getTreasureFactory().getTreasureMapPlayers().forEach(uuid -> {
//            Player player = Bukkit.getPlayer(uuid);
//            if(player == null) {
//                plugin.getTreasureFactory().getTreasureMapPlayers().remove(uuid);
//                return;
//            }
//
//
//        });
    }
}
