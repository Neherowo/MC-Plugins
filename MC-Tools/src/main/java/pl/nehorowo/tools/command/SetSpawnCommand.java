package pl.nehorowo.tools.command;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.utils.TextUtil;
import sun.misc.Unsafe;

import java.util.Collections;
import java.util.List;

public class SetSpawnCommand extends CommandAPI {

    public SetSpawnCommand() {
        super(
                "setspawn",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".setspawn",
                "Ustawia spawn serwera",
                "/setspawn",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }

        if(args.length != 0) {
            sendUsage(player);
            return;
        }

        ToolsPlugin.getInstance().getConfiguration().setSpawnLocation(player.getLocation());
//        ToolsPlugin.getInstance().getConfiguration().load();

        ToolsPlugin.getInstance().getMessageConfiguration()
                .getSetSpawn()
                .send(player);

    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
