package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class GamemodeCommand extends CommandAPI {

    public GamemodeCommand() {
        super(
                "gamemode",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".gamemode",
                "Komenda sluzaca do zarzadzania trybem gry dla gracza(y)",
                "/gamemode <0/1/2/3> [player]",
                Collections.singletonList("gm")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }

        if(args.length == 1) {
            changeGamemode(args[0], player);

            ToolsPlugin.getInstance().getMessageConfiguration()
                    .getChangedGamemode()
                    .addPlaceholder(ImmutableMultimap.of("[GAMEMODE]", player.getGameMode().toString().toUpperCase()))
                    .send(player);
        } else if(args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                getMessageConfiguration()
                        .getIncorrectPlayer()
                        .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[1]))
                        .send(player);
                return;
            }

            changeGamemode(args[0], target);

            getMessageConfiguration()
                    .getChangedGamemode()
                    .addPlaceholder(ImmutableMultimap.of("[GAMEMODE]", target.getGameMode().toString().toUpperCase()))
                    .send(target);

            getMessageConfiguration()
                    .getChangedTargetGamemode()
                    .addPlaceholder(ImmutableMultimap.of(
                            "[PLAYER]", target.getName(),
                            "[GAMEMODE]", target.getGameMode().toString().toUpperCase())
                    )
                    .send(player);

        } else {
            sendUsage(player);
        }

    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return List.of("0", "1", "2", "3", "survival", "creative", "adventure", "spectator");
        else if(args.length == 2) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else return null;
    }

    private void changeGamemode(String args, Player player) {
        switch (args.toLowerCase()) {
            case "0", "survival", "sv" -> player.setGameMode(GameMode.SURVIVAL);
            case "1", "creative", "cr" -> player.setGameMode(GameMode.CREATIVE);
            case "2", "adventure", "ad" -> player.setGameMode(GameMode.ADVENTURE);
            case "3", "spectator", "sp" -> player.setGameMode(GameMode.SPECTATOR);
            default -> getMessageConfiguration()
                    .getIncorrectGameMode()
                    .addPlaceholder(ImmutableMultimap.of("[GAMEMODE]", args.toUpperCase()))
                    .send(player);
        }

    }
}
