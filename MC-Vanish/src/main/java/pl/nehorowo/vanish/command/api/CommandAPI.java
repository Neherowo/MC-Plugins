package pl.nehorowo.vanish.command.api;

import com.google.common.collect.ImmutableMultimap;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.vanish.VanishPlugin;
import pl.nehorowo.vanish.configuration.Configuration;
import pl.nehorowo.vanish.configuration.MessageConfiguration;
import pl.nehorowo.vanish.factory.VanishFactory;
import pl.nehorowo.vanish.uti.TextUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CommandAPI extends BukkitCommand {

    private final String permission;

    private final VanishPlugin instance = VanishPlugin.getInstance();
    private final Configuration configuration = instance.getConfiguration();
    private final MessageConfiguration messageConfiguration = instance.getMessageConfiguration();
    private final VanishFactory vanishFactory = instance.getVanishFactory();

    @SneakyThrows
    public CommandAPI(@NotNull String name, String permission, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.permission = permission;
        this.setPermission(permission);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if(permission == null || permission.isEmpty() || commandSender.hasPermission(permission)) {
            try {
                execute(commandSender, strings);
            } catch (Exception e) {
                String message = e.getMessage();

                if(commandSender.hasPermission(getConfiguration().getPermissionPrefix() + ".cmd.admin")) TextUtil.sendMessage(commandSender, "&cWystąpił błąd poczas wykonywania polecenia! Błąd: &4" + message);
                else TextUtil.sendMessage(commandSender, "&cWystąpił błąd poczas wykonywania polecenia!");
                Bukkit.getLogger().warning("Gracz " + commandSender.getName() + " wykonal polecenie " + s + ". Przy wykonywaniu jej wystąpil blad: " + message);
                e.printStackTrace();
                return false;
            }
        } else {
            messageConfiguration
                    .getNoPermission()
                    .send((Player) commandSender);
        }
        return false;
    }

    @Override
    public @NonNull List<String> tabComplete(@NonNull CommandSender sender, @NonNull String label, @NotNull String[] args) {
        List<String> completions = tab((Player) sender, args);
        if (completions == null) return new ArrayList<>();
        else return completions;
    }

    public void sendUsage(Player player) {
        messageConfiguration.getCorrectUsage()
                .addPlaceholder(ImmutableMultimap.of("[USAGE]", getUsage()))
                .send(player);
    }

    public abstract void execute(CommandSender sender, String[] args);
    public abstract List<String> tab(@NonNull Player player, @NonNull String[] args);

}