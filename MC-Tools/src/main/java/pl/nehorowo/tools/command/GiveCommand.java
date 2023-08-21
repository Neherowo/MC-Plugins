package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GiveCommand extends CommandAPI {

    public GiveCommand() {
        super(
           "give",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".give",
                "Gave player item(s)",
                "/give <item> <amount> [player]",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cTylko gracz moze uzyc tej komendy!");
            return;
        }

        if(args.length < 2) {
            sendUsage(player);
            return;
        }

        if(args.length == 2) {
            String item = args[0].replace("_", " ");
            Material material = Material.matchMaterial(item);
            int amount = Integer.parseInt(args[1]);

            if(material == null) {
                getMessageConfiguration()
                        .getIncorrectItem()
                        .send(player);
                return;
            }

            player.getInventory().addItem(new ItemStack(material, amount));
            getMessageConfiguration()
                    .getGaveItem()
                    .addPlaceholder(ImmutableMultimap.of("[AMOUNT]", String.valueOf(amount), "[ITEM]", item))
                    .send(player);
        } else if(args.length == 3) {
            String item = args[0].replace("_", " ");
            Material material = Material.matchMaterial(item);
            int amount = Integer.parseInt(args[1]);
            Player target = Bukkit.getPlayer(args[2]);

            if(material == null) {
                getMessageConfiguration()
                        .getIncorrectItem()
                        .send(player);
                return;
            }

            if(target == null) {
                getMessageConfiguration()
                        .getIncorrectPlayer()
                        .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[2]))
                        .send(player);
                return;
            }

            target.getInventory().addItem(new ItemStack(material, amount));
            getMessageConfiguration()
                    .getGaveItemTarget()
                    .addPlaceholder(ImmutableMultimap.of(
                            "[AMOUNT]", String.valueOf(amount),
                            "[ITEM]", item,
                            "[PLAYER]", target.getName())
                    )
                    .send(player);
            return;
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if (args.length == 1) return Arrays.stream(Material.values()).map(Enum::name).toList();
        else if(args.length == 2) return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        else if (args.length == 3) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else return null;
    }
}
