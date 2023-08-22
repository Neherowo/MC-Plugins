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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MsgCommand extends CommandAPI {

    public MsgCommand() {
        super(
                "msg",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".msg",
                "Komenda sluzaca do pisania wiadomosci prywatnych",
                "/msg <gracz> <wiadomsoc>",
                Collections.singletonList("w")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 2) {
            sendUsage((Player) sender);
            return;
        }


        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            ToolsPlugin.getInstance().getMessageConfiguration().getIncorrectPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                    .send((Player) sender);
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        if(sender instanceof Player player) {
            UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> user.setLastMsg(player));
            UserService.getInstance().get(target.getUniqueId()).ifPresent(tUser -> tUser.setLastMsg(player));
        }

        Bukkit.getOnlinePlayers().forEach(players ->
            UserService.getInstance().get(players.getUniqueId()).ifPresent(user -> {
            if(user.isSocialSpy()) {
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
                        "[PLAYER]", sender.getName()
                ))
                .send(target);

    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else return null;
    }
}
