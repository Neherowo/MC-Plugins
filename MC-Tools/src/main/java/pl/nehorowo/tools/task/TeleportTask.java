package pl.nehorowo.tools.task;

import com.google.common.collect.ImmutableMultimap;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.factory.UserFactory;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.user.teleport.Teleport;

public record TeleportTask(ToolsPlugin plugin) implements Runnable {

    @Override
    public void run() {
        plugin.getTeleportFactory().getTeleports().entrySet().removeIf(entry -> {
            Player player = entry.getKey();
            if (player == null) return true;

            Teleport teleport = entry.getValue();
            if (player.getLocation().distance(teleport.getFrom()) > 0.5) {
                ToolsPlugin.getInstance().getMessageConfiguration().getTeleportCancelled()
                        .send(player);
                plugin.getTeleportFactory().removeTeleport(player);
                return true;
            }

            User user = plugin.getUserFactory().findUser(player.getUniqueId());
            ToolsPlugin.getInstance().getMessageConfiguration().getTimeToTeleport()
                    .addPlaceholder(ImmutableMultimap.of("[TIME]", String.valueOf(user.getTeleportDelay())))
                    .send(player);

            if (user.getTeleportDelay() <= 0) {
                ToolsPlugin.getInstance().getMessageConfiguration().getTeleported()
                        .send(player);

                //ludzie kiedy #teleportAsync :O
                player.teleportAsync(teleport.getTo());
                plugin.getTeleportFactory().removeTeleport(player);
                return true;
            } else {
                user.setTeleportDelay(user.getTeleportDelay() - 1);
                return false;
            }
        });
    }
}
