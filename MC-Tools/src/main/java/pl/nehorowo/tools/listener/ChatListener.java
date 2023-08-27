package pl.nehorowo.tools.listener;

import com.google.common.collect.ImmutableMultimap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.utils.TimeUtil;

public record ChatListener(ToolsPlugin plugin) implements Listener {

    public ChatListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler @SuppressWarnings("deprecation")
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
            if (player.hasPermission(plugin.getConfiguration().getAdminPermission() + ".chat.bypass")) {
                plugin.getChatController().addMessageToPlayerHistory(user, event.getMessage());
                plugin.getChatBubbleController().createHolo(event.getMessage(), player.getLocation(), user);

                //hover :O
//            TextComponent component = new TextComponent(event.getMessage());
//            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.minecraft.net/"));
//            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Minecraft")));
//            Bukkit.broadcast(component);
//            event.setCancelled(true);
                return;
            }


            // CHAT MANAGER (CD FOR TYPING)
            if (plugin.getChatController().hasChatCooldown(user)) {
                plugin.getMessageConfiguration()
                        .getYouHaveCooldown()
                        .addPlaceholder(ImmutableMultimap.of(
                                "[TIME]", TimeUtil.longToString(user.getLastMessage() / 1000)
                        ))
                        .send(player);
                event.setCancelled(true);
                return;
            } else {
                plugin.getChatBubbleController().createHolo(event.getMessage(), player.getLocation(), user);

                plugin.getChatController().addMessageToPlayerHistory(user, event.getMessage());
                plugin.getChatController().setLastMessage(user, System.currentTimeMillis() + (plugin.getConfiguration().getChatCooldown() * 1000L));
            }

            // CHAT MANAGER (STATUS)
            if (!plugin.getChatController().isChatStatus()) {
                plugin.getMessageConfiguration()
                        .getChatIsMuted()
                        .send(player);

                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(
                                p -> p.hasPermission(plugin.getConfiguration().getAdminPermission() + ".chat.bypass"))
                        .forEach(p ->
                                plugin.getMessageConfiguration()
                                        .getPlayerTriedToMute()
                                        .addPlaceholder(ImmutableMultimap.of(
                                                "[PLAYER]", player.getName())
                                        )
                                        .send(p)
                        );

                event.setCancelled(true);
            }

        });
    }
}
