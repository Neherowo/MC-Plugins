package pl.nehorowo.reward.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.nehorowo.reward.service.UserService;

public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void handlePlayerQuitEvent(PlayerQuitEvent event) {
        UserService.getInstance().get(event.getPlayer().getUniqueId()).ifPresent(userController ->
                userController.setPlayer(null)
        );
    }
}

