package pl.nehorowo.tools.controller;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.TextUtil;


@Getter
public class ChatController {

    private boolean chatStatus = true;

    public void changeChatStatus(CommandSender sender, boolean status) {
        if (status) {
            if (chatStatus) {
                ToolsPlugin.getInstance().getMessageConfiguration()
                        .getChatIsAlreadyUnMuted()
                        .send((Player) sender);
                return;
            }

            ToolsPlugin.getInstance().getMessageConfiguration()
                    .getUnMutedChat()
                    .send((Player) sender);

            ToolsPlugin.getInstance().getMessageConfiguration()
                    .getUnMutedChatBroadcast().forEach(message ->
                            Bukkit.getOnlinePlayers().forEach(player ->
                                    TextUtil.sendMessage(player, message
                                            .replace("[PLAYER]", sender.getName())
                                    )
                            )
                    );

            chatStatus = true;
        } else {
            if (!chatStatus) {
                ToolsPlugin.getInstance().getMessageConfiguration()
                        .getChatIsAlreadyMuted()
                        .send((Player) sender);
                return;
            }

            ToolsPlugin.getInstance().getMessageConfiguration()
                    .getMutedChat()
                    .send((Player) sender);

            ToolsPlugin.getInstance().getMessageConfiguration()
                    .getUnMutedChatBroadcast().forEach(message ->
                            Bukkit.getOnlinePlayers().forEach(player ->
                                    TextUtil.sendMessage(player, message
                                            .replace("[PLAYER]", sender.getName())
                                    )
                            )
                    );
            chatStatus = false;
        }
    }

    public void clearChat(CommandSender sender) {
        for (int i = 0; i < 100; i++) Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(" "));

        ToolsPlugin.getInstance().getMessageConfiguration()
                .getClearChat()
                .send((Player) sender);

        ToolsPlugin.getInstance().getMessageConfiguration()
                .getClearChatBroadcast().forEach(message ->
                        TextUtil.sendMessage(sender, message
                                .replace("[PLAYER]", sender.getName())
                        )
                );
    }



    public void setLastMessage(UserController user, long time) {
        user.setLastMessage(time);
    }

    public boolean hasChatCooldown(UserController user) {
        return user.getLastMessage() > System.currentTimeMillis();
    }

    public void addMessageToPlayerHistory(UserController user, String message) {
        user.getMessages().add(message);
    }
}
