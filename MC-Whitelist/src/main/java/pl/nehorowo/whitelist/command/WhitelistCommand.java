package pl.nehorowo.whitelist.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.whitelist.WhitelistPlugin;
import pl.nehorowo.whitelist.command.api.CommandAPI;
import pl.nehorowo.whitelist.menu.WhitelistMenu;
import pl.nehorowo.whitelist.service.WhitelistService;
import pl.nehorowo.whitelist.util.TextUtil;

import java.util.List;

public class WhitelistCommand extends CommandAPI {

    public WhitelistCommand() {
        super(
                "whitelist",
                WhitelistPlugin.getInstance().getConfiguration().getPermissionPrefix() + ".whitelist",
                "Komenda do zarządzania whitelistą",
                "/whitelist <add/remove/status/list> [nick]",
                List.of("wl")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            TextUtil.sendMessage(sender, "&cKomenda tylko dla graczy!");
            return;
        }

        if(args.length == 0) sendUsage(player);
        else if(args.length == 1) {

            switch (args[0].toLowerCase()) {
                case "status" -> {
                    boolean whitelistEnabled = WhitelistService.getInstance().isWhitelistEnabled();

                    getMessageConfiguration()
                            .getWhitelistStatus()
                            .addPlaceholder(ImmutableMultimap.of("[STATUS]", whitelistEnabled ? "&cWlaczona" : "&aWylaczona"))
                            .send(player);
                }

                case "list" -> {
                    String whitelist = WhitelistService.getInstance().getWhitelist();

                    getMessageConfiguration()
                            .getWhitelistListFormat()
                            .addPlaceholder(ImmutableMultimap.of("[WHITELIST]", whitelist))
                            .send(player);
                }

                case "reload" -> {
                    WhitelistPlugin.getInstance().getMessageConfiguration().load();
                    WhitelistPlugin.getInstance().getConfiguration().load();
                    WhitelistPlugin.getInstance().getWhitelistPeopleConfiguration().load();

                    getMessageConfiguration()
                            .getWhitelistReload()
                            .send(player);
                }

                case "gui" -> new WhitelistMenu().open(player);

                case "info" -> {
                    WhitelistService.getInstance().info();
                }
            }

        } else if(args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "add", "dodaj" -> {
                    WhitelistService.getInstance().addPlayer(args[1], player);
                    getMessageConfiguration()
                            .getWhitelistAdd()
                            .addPlaceholder(ImmutableMultimap.of("[NICK]", args[1]))
                            .send(player);
                }

                case "remove", "usun" -> {
                    WhitelistService.getInstance().removePlayer(args[1], player);
                    getMessageConfiguration()
                            .getWhitelistRemove()
                            .addPlaceholder(ImmutableMultimap.of("[NICK]", args[1]))
                            .send(player);
                }
            }
        } else {
            sendUsage(player);
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return List.of("add", "remove", "status", "list", "reload", "gui");
        else if(args.length == 2) return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        else return null;
    }
}
