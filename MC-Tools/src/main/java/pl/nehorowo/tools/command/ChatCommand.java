package pl.nehorowo.tools.command;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.menu.ChatMenu;
import pl.nehorowo.tools.menu.MessageHistoryMenu;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class ChatCommand extends CommandAPI {

    public ChatCommand() {
        super(
                "chat",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".chat",
                "Komenda sluzaca do zarzadzania czatem",
                "/chat <off/on/clear/(<history> <player>)",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            if(!(sender instanceof Player player)) {
                TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
                return;
            }

            new ChatMenu().openChatMenu(player);
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "on", "wlacz" -> getInstance().getChatFactory().changeChatStatus(sender, true);
                case "off", "wylacz" -> getInstance().getChatFactory().changeChatStatus(sender, false);
                case "clear", "wyczysc" -> getInstance().getChatFactory().clearChat(sender);

                default -> sendUsage((Player) sender);
            }
        } else if(args.length == 2 && args[0].equalsIgnoreCase("history")) {
            if(!(sender instanceof Player player)) {
                TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
                return;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                getMessageConfiguration()
                        .getIncorrectPlayer()
                        .send(player);
                return;
            }

            User user = getUserRepository().findUser(target.getUniqueId());
            if(user == null) return;

            if(user.getMessages() == null || user.getMessages().isEmpty()) {
                getMessageConfiguration()
                        .getMessageHistoryIsEmpty()
                        .send(player);
                return;
            }

            new MessageHistoryMenu().openMessageHistoryMenu(player, target);
        }


    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return List.of("on", "off", "clear", "history");
        else if(args.length == 2) if(args[0].equalsIgnoreCase("history")) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        return null;
    }
}
