package pl.nehorowo.tools.task;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.listener.MoveListener;
import pl.nehorowo.tools.service.UserService;

public record ChatBubblesTask(ToolsPlugin plugin) implements Runnable {

    public void run() {
        UserService.getInstance().getUserControllerMap().values().stream().filter(user -> (user.getChatBubble() != null)).forEach(user -> {
            if (System.currentTimeMillis() - user.getChatBubble().getTime() > 3 * 1000) {
                DHAPI.removeHologram(user.getChatBubble().getId());
                user.setChatBubble(null);
            } else {
                Player player = Bukkit.getPlayer(user.getUuid());
                MoveListener.handeChatBubble(user, player, player.getLocation());
            }
        });
    }

}