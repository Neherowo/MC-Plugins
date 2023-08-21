package pl.nehorowo.tools.listener;

import com.google.common.collect.ImmutableMultimap;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.TextUtil;
import pl.nehorowo.tools.utils.TimeUtil;

public record ChatListener(ToolsPlugin plugin) implements Listener {

    public ChatListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler @SuppressWarnings("deprecation")
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) return;

        Player player = event.getPlayer();
        User user = plugin.getUserFactory().findUser(player.getUniqueId());
        if(user == null) return;

        if(player.hasPermission(plugin.getConfiguration().getAdminPermission() + ".chat.bypass")) {
            plugin.getChatFactory().addMessageToPlayerHistory(user, event.getMessage());
            plugin.getChatBubbleFactory().createHolo(event.getMessage(), player.getLocation(), user);

            //hover :O
//            TextComponent component = new TextComponent(event.getMessage());
//            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.minecraft.net/"));
//            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Minecraft")));
//            Bukkit.broadcast(component);
//            event.setCancelled(true);
            return;
        }


        // CHAT MANAGER (CD FOR TYPING)
        if(plugin.getChatFactory().hasChatCooldown(user)) {
            plugin.getMessageConfiguration()
                    .getYouHaveCooldown()
                    .addPlaceholder(ImmutableMultimap.of(
                            "[TIME]", TimeUtil.longToString(user.getLastMessage() / 1000)
                    ))
                    .send(player);
            event.setCancelled(true);
            return;
        } else {
            plugin.getChatBubbleFactory().createHolo(event.getMessage(), player.getLocation(), user);

            plugin.getChatFactory().addMessageToPlayerHistory(user, event.getMessage());
            plugin.getChatFactory().setLastMessage(user, System.currentTimeMillis() + (plugin.getConfiguration().getChatCooldown() * 1000L));
        }

            // CHAT MANAGER (STATUS)
        if(!plugin.getChatFactory().isChatStatus()) {
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
    }

}
