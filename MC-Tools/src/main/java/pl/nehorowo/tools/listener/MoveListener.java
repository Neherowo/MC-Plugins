package pl.nehorowo.tools.listener;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.controller.UserController;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.user.User;

public record MoveListener(ToolsPlugin plugin) implements Listener {

    public MoveListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) return;

        Player player = event.getPlayer();
        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> handeChatBubble(user, player, to));
    }


    public static void handeChatBubble(UserController user, Player p, Location to) {
        if (user.getChatBubble() != null) {
            String holoName = "chat-bubble-" + p.getName();
            Location clone = to.clone();
            clone.add(0.0D, 3, 0.0D);

            Hologram hologram = DHAPI.getHologram(holoName);
            if (hologram != null) DHAPI.moveHologram(hologram, clone);
            else ToolsPlugin.getInstance().getChatBubbleController().createHolo(user.getChatBubble().getText(), to, user);
        }
    }
}

