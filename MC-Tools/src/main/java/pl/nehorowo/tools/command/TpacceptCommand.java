package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.controller.UserController;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class TpacceptCommand extends CommandAPI {

    public TpacceptCommand() {
        super(
                "tpaccept",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".tpaccept",
                "Komenda sluzaca do akceptowania teleportacji",
                "/tpaccept <player>",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }

        if(args.length != 1) {
            ToolsPlugin.getInstance().getMessageConfiguration().getCorrectUsage()
                    .addPlaceholder(ImmutableMultimap.of("[USAGE]", "/tpaccept <gracz/*>"))
                    .send(player);
            return;
        }

        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
            var tpList = user.getTpList();
            tpList.removeIf(players -> players == null || players.isDead() || !players.isOnline());
            if(args[0].equalsIgnoreCase("*")) {
                if (tpList.isEmpty()) {
                    ToolsPlugin.getInstance().getMessageConfiguration().getNoTpRequests()
                            .send(player);
                    return;
                }

                tpList.forEach(players -> {
                    if(tpList == null) return;

                    ToolsPlugin.getInstance().getMessageConfiguration()
                            .getAcceptedRequest()
                            .send(player);
                    ToolsPlugin.getInstance().getTeleportController().addTeleport(players, player.getLocation());
                    user.getTpList().remove(players);
                });
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    ToolsPlugin.getInstance().getMessageConfiguration().getIncorrectPlayer()
                            .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                            .send(player);
                    return;
                }

                if(!user.getTpList().contains(target)) {
                    ToolsPlugin.getInstance().getMessageConfiguration().getNoTpRequestsPlayer()
                            .addPlaceholder(ImmutableMultimap.of("[PLAYER]", target.getName()))
                            .send(player);
                    return;
                }

                ToolsPlugin.getInstance().getTeleportController().addTeleport(target, player.getLocation());
                user.getTpList().remove(target);
                ToolsPlugin.getInstance().getMessageConfiguration()
                        .getAcceptedRequest()
                        .send(player);
            }
        });
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        UserController user = UserService.getInstance().get(player.getUniqueId()).get(); //idk idk
        if(user.getTpList() == null) return null;
        if(args.length == 1) return user.getTpList().stream().map(Player::getName).toList();
        else return null;
    }
}
