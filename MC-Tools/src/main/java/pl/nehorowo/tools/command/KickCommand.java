package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class KickCommand extends CommandAPI {

    public KickCommand() {
        super(
                "kick",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".kick",
                "Komenda sluzaca do wyrzucania grzaczy",
                "/kick <gracz> <powod>",
                Collections.singletonList("wyrzuc")
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

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            getMessageConfiguration()
                    .getIncorrectPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                    .send(player);
            return;
        }

        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        target.kickPlayer(TextUtil.fixColor(
                getConfiguration().getKickPlayerFormat()
                    .replace("[REASON]", reason))
        );

        Bukkit.broadcastMessage(TextUtil.fixColor(
                getMessageConfiguration().getKickedPlayerBroadcast()
                    .replace("[REASON]", reason)
                    .replace("[PLAYER]", target.getName()))
        );
    }


    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else return null;
    }
}
