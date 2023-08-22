package pl.nehorowo.tools.task;

import com.google.common.collect.ImmutableMultimap;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.module.Teleport;

public record TeleportTask(ToolsPlugin plugin) implements Runnable {

    @Override
    public void run() {
        plugin.getTeleportController().getTeleports().entrySet().removeIf(entry -> {
            Player player = entry.getKey();
            if (player == null) return true;

            Teleport teleport = entry.getValue();
            if (player.getLocation().distance(teleport.getFrom()) > 0.5) {
                ToolsPlugin.getInstance().getMessageConfiguration().getTeleportCancelled()
                        .send(player);
                plugin.getTeleportController().removeTeleport(player);
                return true;
            }

            UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
                ToolsPlugin.getInstance().getMessageConfiguration().getTimeToTeleport()
                        .addPlaceholder(ImmutableMultimap.of("[TIME]", String.valueOf(user.getTeleportDelay())))
                        .send(player);

                if (user.getTeleportDelay() <= 0) {
                    ToolsPlugin.getInstance().getMessageConfiguration().getTeleported()
                            .send(player);

                    //ludzie kiedy #teleportAsync :O
                    player.teleportAsync(teleport.getTo());
                    plugin.getTeleportController().removeTeleport(player);
                } else {
                    user.setTeleportDelay(user.getTeleportDelay() - 1);
                }
            });
            return false;
        });
    }
}
