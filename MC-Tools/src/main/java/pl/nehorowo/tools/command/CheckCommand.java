package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CheckCommand extends CommandAPI {

    public CheckCommand() {
        super(
                "check",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".check",
                "Komenda sluzaca do sprawdzania gracza",
                "/check <check/clear/ban> <player>",
                Collections.singletonList("sprawdz")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cTa komenda jest dostepna tylko dla graczy!");
            return;
        }

        if(args.length <= 1) {
            sendUsage(player);
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            getMessageConfiguration()
                    .getIncorrectPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[1]))
                    .send(player);
            return;
        }
        User user = getUserRepository().findUser(target.getUniqueId());
        if(user == null) return;

        switch (args[0].toLowerCase()) {
            case "check", "sprawdz" -> {
                if(user.getCheck().isChecked()) {
                    getMessageConfiguration()
                            .getAlreadyChecked()
                            .send(player);
                    return;
                }

                user.getCheck().setChecked(true);
                user.getCheck().setPlayerLocation(target.getLocation());

                player.teleportAsync(getConfiguration().getCheckLocation());
            }


            case "clear", "czysty" -> {
                if(!user.getCheck().isChecked()) {
                    getMessageConfiguration()
                            .getNotChecked()
                            .send(player);
                    return;
                }

                player.teleportAsync(user.getCheck().getPlayerLocation());

                user.getCheck().setChecked(false);
                user.getCheck().setPlayerLocation(null);
            }

            case "ban", "cheater" -> {
                if(!user.getCheck().isChecked()) {
                    getMessageConfiguration()
                            .getNotChecked()
                            .send(player);
                    return;
                }

                user.getCheck().setChecked(false);
                user.getCheck().setPlayerLocation(null);
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        getConfiguration().getBanCommand()
                                .replace("[PLAYER]", target.getName())
                );
             }

            default -> sendUsage((Player) sender);
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if (args.length == 1) return Set.of("check", "clear", "ban").stream().toList();
        else if(args.length == 2) return Bukkit.getOnlinePlayers().stream().filter(
                target -> !target.hasPermission(ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".check.bypass"))
                .map(Player::getName).toList();
        else return null;
    }
}
