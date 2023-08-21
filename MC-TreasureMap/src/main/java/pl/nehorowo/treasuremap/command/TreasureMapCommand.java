package pl.nehorowo.treasuremap.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.treasuremap.TreasureMapPlugin;
import pl.nehorowo.treasuremap.command.api.CommandAPI;
import pl.nehorowo.treasuremap.uti.TextUtil;

import java.util.Collections;
import java.util.List;

public class TreasureMapCommand extends CommandAPI {

    public TreasureMapCommand() {
        super(
                "treasuremap",
                TreasureMapPlugin.getInstance().getConfiguration().getPermissionPrefix() + ".treasuremap",
                "Wiadomosc sluzaca do zarzadzania skarbami",
                "/treasuremap <reload/(give> <gracz)>",
                Collections.emptyList()

        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cKomenda dostepna tylko dla graczy!");
            return;
        }

        if(args.length == 0) {
            sendUsage(player);
            return;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            TreasureMapPlugin.getInstance().reloadConfig();

            getMessageConfiguration()
                    .getReloaded()
                    .send(player);
        } else if(args.length == 2 && args[0].equalsIgnoreCase("give")) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                getMessageConfiguration()
                        .getIncorrectPlayer()
                        .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[1]))
                        .send(player);
                return;
            }


        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
