package pl.nehorowo.tools.command;

import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class ToolsCommand extends CommandAPI {

    public ToolsCommand() {
        super(
                "tools",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".tools",
                "Komenda sluzaca do zarzadzania toolsami",
                "/tools",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        getInstance().getMessageConfiguration().load();
        getInstance().getConfiguration().load();

        TextUtil.sendMessage(sender, "&aPrzeladowano konfiguracje!");
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
