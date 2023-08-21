package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class HealCommand extends CommandAPI {

    public HealCommand() {
        super(
                "heal",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".heal",
                "Komenda sluzaca do uleczenia siebie",
                "/heal [player]",
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
            player.setFoodLevel(20);
            player.setHealth(player.getMaxHealth());

            getMessageConfiguration()
                    .getFeed()
                    .send(player);
        } else if(args.length == 1) {
            if(!player.hasPermission(getConfiguration().getAdminPermission() + ".heal.target")) {
                getMessageConfiguration()
                        .getNoPermission()
                        .send(player);
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                getMessageConfiguration()
                        .getIncorrectPlayer()
                        .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                        .send(player);
                return;
            }

            target.setFoodLevel(20);
            target.setHealth(target.getMaxHealth());
            getMessageConfiguration()
                    .getHealedPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", target.getName()))
                    .send(player);

            getMessageConfiguration()
                    .getHealedTarget()
                    .send(target);
        } else {
            sendUsage(player);
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else return null;
    }
}
