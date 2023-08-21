package pl.nehorowo.tools.command;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Arrays;
import java.util.List;

public class BroadcastCommand extends CommandAPI {

    public BroadcastCommand() {
        super(
                "broadcast",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".broadcast",
                "Komenda sluzaca do wysylania wiadomosci do wszystkich graczy",
                "/broadcast <title/subtitle/actionbar/message> <message>",
                List.of("bc")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length <= 1) {
            sendUsage((Player)sender);
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        switch (args[0].toLowerCase()) {
            case "title" -> Bukkit.getOnlinePlayers().forEach(players -> TextUtil.sendTitle(players, message, ""));
            case "subtitle" -> Bukkit.getOnlinePlayers().forEach(players -> TextUtil.sendTitle(players, "", message));
            case "actionbar" -> Bukkit.getOnlinePlayers().forEach(players -> TextUtil.sendActionBar(players, message));
            case "message" -> Bukkit.broadcastMessage(TextUtil.fixColor(message));
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return List.of("title", "subtitle", "actionbar", "message");

        else return null;
    }
}
