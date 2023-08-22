package pl.nehorowo.tools.controller;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.module.Teleport;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TeleportController {

    @Getter private final ConcurrentMap<Player, Teleport> teleports = new ConcurrentHashMap<>();

    public void addTeleport(Player player, Location location) {
        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
            if(player.hasPermission(ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".teleport.admin"))
                user.setTeleportDelay(0);
            else if(player.hasPermission(ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".teleport.vip"))
                user.setTeleportDelay(ToolsPlugin.getInstance().getConfiguration().getVipTeleportTime());
            else if(player.hasPermission(ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".teleport.svip"))
                user.setTeleportDelay(ToolsPlugin.getInstance().getConfiguration().getSvipTeleportTime());

            else
                user.setTeleportDelay(ToolsPlugin.getInstance().getConfiguration().getPlayerTeleportTime());

            teleports.put(player, new Teleport(player.getLocation(), location));
        });
    }

    public boolean containsTeleport(Player player) {
        return teleports.containsKey(player);
    }
    public void removeTeleport(Player player) {
        teleports.remove(player);
    }


}
