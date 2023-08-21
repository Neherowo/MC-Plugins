package pl.nehorowo.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.TimeUtil;

public record LoginListener(ToolsPlugin plugin) implements Listener {


    public LoginListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if(plugin.getBanFactory().findBan(player.getUniqueId()) == null) {
            System.out.println("player is not banned - TODO"); //TODO
            return;
        }


        event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        event.setKickMessage(
                plugin.getConfiguration()
                        .getBanPlayerRemainingFormat()
                            .replace("[REASON]", plugin.getBanFactory().findBan(player.getUniqueId()).getReason())
                            .replace("[TIME]", TimeUtil.longToString(plugin.getBanFactory().findBan(player.getUniqueId()).getTime()))
                            .replace("[ADMIN]", plugin.getBanFactory().findBan(player.getUniqueId()).getAdmin().getName())
        );
    }
}
