package pl.nehorowo.tools.listener;

import com.google.common.collect.ImmutableMultimap;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.factory.UserFactory;
import pl.nehorowo.tools.user.User;

public record JoinQuitListener(ToolsPlugin plugin) implements Listener {


    public JoinQuitListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(plugin.getUserFactory().findUser(player.getUniqueId()) == null) plugin.getUserFactory().createUser(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = plugin.getUserFactory().findUser(player.getUniqueId());
        if (user == null) return;

        if(user.getCheck().isChecked()) {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    plugin.getConfiguration().getBanLogoutCommand().replace("[PLAYER]", player.getName())
            );
        }

        if(plugin.getCombatFactory().isInCombat(player)) {
            Bukkit.getOnlinePlayers().forEach(players ->
                    plugin.getMessageConfiguration()
                        .getPlayerLoggedWhileCombat()
                            .addPlaceholder(ImmutableMultimap.of("[PLAYER]", player.getName()))
                    .send(players)
            );

            player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
            player.setHealth(0);

            plugin.getCombatFactory().removeCombat(player);
        }

        if(user.getChatBubble() != null) {
            DHAPI.removeHologram(user.getChatBubble().getId());
            plugin.getUserFactory().removeUser(player.getUniqueId());
        }
    }
}
