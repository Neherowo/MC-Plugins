package pl.nehorowo.vanish.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.nehorowo.vanish.VanishPlugin;

public record JoinQuitListener(VanishPlugin plugin) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(plugin.getVanishFactory().getVanishedPlayers().contains(player.getUniqueId())) {
            Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(plugin, player));
            event.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(plugin.getVanishFactory().getVanishedPlayers().contains(player.getUniqueId())) {
            event.setQuitMessage(null);
        }
    }


}
