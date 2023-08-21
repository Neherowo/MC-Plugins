package pl.nehorowo.tools.command;

import fr.minuskube.inv.SmartInventory;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.menu.SpawnMenu;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class SpawnCommand extends CommandAPI {

    public SpawnCommand() {
        super(
                "spawn",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".spawn",
                "Komenda sluzaca do teleportowania na spawn",
                "/spawn",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }


        new SpawnMenu().openSpawnMenu(player);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
