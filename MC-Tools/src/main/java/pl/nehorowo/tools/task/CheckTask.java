package pl.nehorowo.tools.task;

import org.bukkit.Bukkit;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.TextUtil;

public record CheckTask(ToolsPlugin plugin) implements Runnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers()
            .forEach(player -> {
                UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
                    if(!user.getCheck().isChecked()) return;

                    TextUtil.sendTitle(
                            player,
                            plugin.getMessageConfiguration().getCheckReminderTitle(),
                            plugin.getMessageConfiguration().getCheckReminderSubTitle()
                    );

                    plugin.getMessageConfiguration()
                            .getCheckRemiderMessage().forEach(message ->
                                    TextUtil.sendMessage(player, message)
                            );
            });
        });
    }
}
