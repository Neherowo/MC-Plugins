package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.service.UserService;

import java.util.Collections;
import java.util.List;

public class ReplyCommand extends CommandAPI {


    public ReplyCommand() {
        super(
                "reply",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".msg",
                "Komenda sluzaca do odpowiadania wiadomosci prywatnych",
                "/r <wiadomsoc>",
                Collections.singletonList("r")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0) {
            ToolsPlugin.getInstance().getMessageConfiguration().getCorrectUsage()
                    .addPlaceholder(ImmutableMultimap.of("[USAGE]", "/reply <wiadomosc>"))
                    .send(player);
            return;
        }

        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
            if(user.getLastMsg() == null) {
                ToolsPlugin.getInstance().getMessageConfiguration().getNoLastMsg()
                        .send(player);
                return;
            }

            Player target = user.getLastMsg();
            String message = String.join(" ", args);

            Bukkit.getOnlinePlayers().forEach(players ->
                    UserService.getInstance().get(players.getUniqueId()).ifPresent(users -> {
                        if(users.isSocialSpy()) {
                            ToolsPlugin.getInstance().getMessageConfiguration().getMsgSpyFormat()
                                    .addPlaceholder(ImmutableMultimap.of(
                                            "[MESSAGE]", message,
                                            "[PLAYER]", sender.getName(),
                                            "[TARGET]", target.getName()
                                    ))
                                    .send(players);
                        }
                    }));

            ToolsPlugin.getInstance().getMessageConfiguration().getMsgSenderFormat()
                    .addPlaceholder(ImmutableMultimap.of(
                            "[MESSAGE]", message,
                            "[PLAYER]", target.getName()
                    ))
                    .send((Player) sender);

            ToolsPlugin.getInstance().getMessageConfiguration().getMsgTargetFormat()
                    .addPlaceholder(ImmutableMultimap.of(
                            "[MESSAGE]", message,
                            "[PLAYER]", player.getName()
                    ))
                    .send(target);

            return;
        });


    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
