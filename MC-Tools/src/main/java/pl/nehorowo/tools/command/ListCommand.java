package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;

import java.util.Collections;
import java.util.List;

public class ListCommand extends CommandAPI {

    public ListCommand() {
        super(
                "list",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".list",
                "Komenda sluzaca do wyswietlania listy graczy online.",
                "/list <size/players>",
                Collections.emptyList());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sendUsage((Player) sender);
            return;
        }


        StringBuilder builder = new StringBuilder();
        Bukkit.getOnlinePlayers().forEach(player -> builder.append(player.getName()).append(", "));
        switch (args[0].toLowerCase()) {
            case "size" -> getMessageConfiguration().getListPlayersSize()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYERS]", Bukkit.getOnlinePlayers().size()))
                    .send((Player)sender);

            case "players" -> getMessageConfiguration().getListPlayers()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYERS]", builder.toString()))
                    .send((Player)sender);

            default -> sendUsage((Player) sender);
        }



    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) return List.of("size", "players");
        else return null;
    }
}
