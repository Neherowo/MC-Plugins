package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;

public class SocialSpyCommand extends CommandAPI {


    public SocialSpyCommand() {
        super(
                "socialspy",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".socialspy",
                "Komenda sluzaca do sledzenia wiadomosci prywatnych",
                "/socialspy",
                Collections.singletonList("sspy")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cPodana komenda jest dostepna tylko dla graczy!");
            return;
        }

        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
            user.setSocialSpy(!user.isSocialSpy());
            ToolsPlugin.getInstance().getMessageConfiguration().getMsgSpyToggle()
                    .addPlaceholder(ImmutableMultimap.of(
                            "[STATUS]", user.isSocialSpy() ? "&aWlaczony" : "&cWylaczony")
                    )
                    .send(player);
        });
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
