package pl.nehorowo.vanish.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.nehorowo.vanish.VanishPlugin;

public record VanishTask(VanishPlugin plugin) implements Runnable {

    @Override
    public void run() {
        plugin.getVanishFactory().getVanishedPlayers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) return;

            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> !p.hasPermission(plugin.getConfiguration().getPermissionPrefix() + ".vanish.see"))
                    .forEach(p -> p.hidePlayer(plugin, player));
        });
    }
}
