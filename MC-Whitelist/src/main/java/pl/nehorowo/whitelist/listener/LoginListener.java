package pl.nehorowo.whitelist.listener;

import com.google.common.collect.ImmutableMultimap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.nehorowo.whitelist.WhitelistPlugin;
import pl.nehorowo.whitelist.service.WhitelistService;

public record LoginListener(WhitelistPlugin plugin) implements Listener {

    public LoginListener(WhitelistPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handlePlayerConnect(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (WhitelistService.getInstance().isWhitelisted(player.getName())) return;
        if(player.hasPermission(plugin.getConfiguration().getPermissionPrefix() + ".bypass")) return;

        event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, plugin.getConfiguration().getNotWhitelistedMessage());
        Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(plugin.getConfiguration().getPermissionPrefix() + ".notify"))
                .forEach(p ->
                        plugin.getMessageConfiguration().getPlayerIsNotWhitelistedBroadcast()
                                .addPlaceholder(ImmutableMultimap.of("[NICK]", player.getName()))
                                .send(p)
                );
    }
}
