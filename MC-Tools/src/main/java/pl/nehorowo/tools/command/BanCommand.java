package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.user.ban.Ban;
import pl.nehorowo.tools.utils.TextUtil;
import pl.nehorowo.tools.utils.TimeUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BanCommand extends CommandAPI {

    public BanCommand() {
        super(
                "ban",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".ban",
                "Komenda sluzaca do banowania graczy",
                "/ban <gracz> <czas> <powod>",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length <= 2) {
            sendUsage((Player)sender);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            getMessageConfiguration()
                    .getIncorrectPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                    .send((Player) sender);
            return;
        }

        String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        long time = TimeUtil.parseTime(args[1]);
        if(time == 0L) {
            getMessageConfiguration()
                    .getIncorrectTime()
                    .send((Player) sender);
            return;
        }

        Ban ban = getInstance().getBanFactory().findBan(target.getUniqueId());
        if(ban != null) {
            System.out.println("player is already banned - TODO"); //TODO
            return;
        }

        getInstance().getBanFactory().addBan(new Ban(
                (Player) sender,
                target,
                time,
                reason
        ));

        target.kickPlayer(TextUtil.fixColor(
                getConfiguration().getBanPlayerFormat()
                        .replace("[REASON]", reason)
                        .replace("[TIME]", args[1])
                        .replace("[ADMIN]", sender.getName())

        ));

        Bukkit.broadcastMessage(TextUtil.fixColor(
                getMessageConfiguration().getBannedPlayerBroadcast()
                        .replace("[REASON]", reason)
                        .replace("[TIME]", args[1])
                        .replace("[ADMIN]", sender.getName())
                        .replace("[PLAYER]", target.getName())
        ));
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else if(args.length == 2) return List.of("1d", "1h", "1m", "1s");
        else return null;
    }
}
