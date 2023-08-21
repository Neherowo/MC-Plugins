package pl.nehorowo.rtp.command;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.rtp.RtpPlugin;
import pl.nehorowo.rtp.command.api.CommandAPI;
import pl.nehorowo.rtp.menu.RtpMenu;
import pl.nehorowo.rtp.utils.TextUtil;

import java.util.List;

public class RtpCommand extends CommandAPI {

    public RtpCommand() {
        super(
                "rtp",
                RtpPlugin.getInstance().getConfiguration().getPermissionPrefix() + ".rtp",
                "Komenda sluzaca do teleportowania na losowe miejsce",
                "/rtp",
                List.of("randomtp", "randomteleport")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }

        if(args.length == 0) new RtpMenu().openRtpMenu(player);

        else if(args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                getInstance().getMessageConfiguration().load();
                getInstance().getConfiguration().load();

                getMessageConfiguration()
                        .getSuccessfullyTeleported()
                        .send(player);
            }
        }


    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) if(player.hasPermission("rtp.reload")) return List.of("reload");
        return null;
    }
}
