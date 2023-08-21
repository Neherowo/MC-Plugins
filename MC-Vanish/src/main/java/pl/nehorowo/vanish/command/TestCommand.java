package pl.nehorowo.vanish.command;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.vanish.command.api.CommandAPI;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class TestCommand extends CommandAPI {

    public TestCommand() {
        super(
                "test",
                "test",
                "test",
                "/test",
                Collections.singletonList("t")
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
