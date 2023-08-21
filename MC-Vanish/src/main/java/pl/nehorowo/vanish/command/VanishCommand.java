package pl.nehorowo.vanish.command;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.vanish.VanishPlugin;
import pl.nehorowo.vanish.command.api.CommandAPI;
import pl.nehorowo.vanish.uti.TextUtil;

import java.util.Collections;
import java.util.List;

public class VanishCommand extends CommandAPI {

    public VanishCommand() {
        super(
                "vanish",
                VanishPlugin.getInstance().getConfiguration().getPermissionPrefix() + ".vanish",
                "vanish",
                "/vanish [player]",
                Collections.singletonList("v")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }

        if(args.length == 0) getVanishFactory().changeVanishStatus(player);
        else if(args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "list" -> {
                    getMessageConfiguration()
                            .getVanishList()
                            .send(player);

                    getInstance().getVanishFactory()
                            .getVanishedPlayers().forEach(players -> {
                                String vanishListFormat = getMessageConfiguration()
                                        .getVanishListFormat()
                                        .replace(
                                                "[PLAYER]", Bukkit.getPlayer(players).getName()
                                        );

                                TextUtil.sendMessage(player, vanishListFormat);
                            });
                }

                case "reload" -> {
                    getInstance().getConfiguration().load();
                    getInstance().getMessageConfiguration().load();
                    getMessageConfiguration()
                            .getReloaded()
                            .send(player);
                }

                default -> {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target == null) {
                        getMessageConfiguration()
                                .getIncorrectPlayer()
                                .send(player);
                        return;
                    }

                    getVanishFactory().changeVanishStatus(target);
                    Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(getInstance(), target));
                }
            }


        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return List.of("list", "reload", "[ GRACZ ]");
        else return null;
    }
}
