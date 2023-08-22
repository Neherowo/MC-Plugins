package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class TpaCommand extends CommandAPI {


    public TpaCommand() {
        super(
                "tpa",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".tpa",
                "Komenda sluzaca do teleportowania do gracza",
                "/tpa <player>",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }

        if(args.length == 0) {
            ToolsPlugin.getInstance().getMessageConfiguration().getCorrectUsage()
                    .addPlaceholder(ImmutableMultimap.of("[USAGE]", "/tpa <gracz>"))
                    .send(player);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            ToolsPlugin.getInstance().getMessageConfiguration().getIncorrectPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                    .send(player);
            return;
        }

        if(player == target) {
            ToolsPlugin.getInstance().getMessageConfiguration()
                    .getCantTeleportToYourself()
                    .send(player);
            return;
        }

        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
            if(ToolsPlugin.getInstance().getTeleportController().containsTeleport(player) || user.getTpList().contains(player)) {
                ToolsPlugin.getInstance().getMessageConfiguration()
                        .getAlreadyTeleported()
                        .send(player);
                return;
            }

            ToolsPlugin.getInstance().getMessageConfiguration().getTpaTargetRequest()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", player.getName()))
                    .send(target);

            ToolsPlugin.getInstance().getMessageConfiguration().getTpaPlayerRequest()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", target.getName()))
                    .send(player);

            user.getTpList().add(player);
        });
        return;
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else return null;
    }
}
