package pl.nehorowo.tools.listener;

import com.google.common.collect.ImmutableMultimap;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.service.UserService;

public record JoinQuitListener(ToolsPlugin plugin) implements Listener {


    public JoinQuitListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        UserService.getInstance().compute(event.getPlayer().getUniqueId()).thenAccept(userController -> {
            userController.setPlayer(event.getPlayer());
            userController.setName(event.getPlayer().getName());

            //im thinking abt this
            userController.update();
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserService.getInstance().get(event.getPlayer().getUniqueId()).ifPresent(userController -> {

            if (userController.getCheck().isChecked()) {
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        plugin.getConfiguration().getBanLogoutCommand().replace("[PLAYER]", player.getName())
                );
            }

            if (plugin.getCombatController().isInCombat(player)) {
                Bukkit.getOnlinePlayers().forEach(players ->
                        plugin.getMessageConfiguration()
                                .getPlayerLoggedWhileCombat()
                                .addPlaceholder(ImmutableMultimap.of("[PLAYER]", player.getName()))
                                .send(players)
                );

                player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
                player.setHealth(0);

                plugin.getCombatController().removeCombat(player);
            }

            if (userController.getChatBubble() != null) {
                DHAPI.removeHologram(userController.getChatBubble().getId());
            }
            userController.setPlayer(null);
        });
    }
}
