package pl.nehorowo.tools.command;

import com.google.common.collect.ImmutableMultimap;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.command.api.CommandAPI;
import pl.nehorowo.tools.utils.TextUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TpCommand extends CommandAPI {


    public TpCommand() {
        super(
                "tp",
                ToolsPlugin.getInstance().getConfiguration().getAdminPermission() + ".tp",
                "Komenda sluzaca do teleportowania do gracza, kordynatow",
                "/tp <player/x> <player2/y> [z] [w przypadku x,y,z - player",
                Collections.emptyList()
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sendUsage((Player)sender);
            return;
        }

        else if (args.length == 1) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                ToolsPlugin.getInstance().getMessageConfiguration()
                        .getIncorrectPlayer()
                        .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                        .send(player);
                return;
            }

            player.teleportAsync(target.getLocation());
            ToolsPlugin.getInstance().getMessageConfiguration().getTeleportedToPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                    .send(player);
            return;
        }
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            Player target2 = Bukkit.getPlayer(args[1]);
            if (target2 == null || target == null) {
                ToolsPlugin.getInstance().getMessageConfiguration().getIncorrectPlayer()
                        .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                        .send((Player) sender);
                return;
            }
            target.teleportAsync(target2.getLocation());
            ToolsPlugin.getInstance().getMessageConfiguration().getTeleportedToPlayer()
                    .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[1]))
                    .send(target);
            return;
        }
        if (args.length == 3) {
            int x, y, z;
            Player p = (Player) sender;
            try {
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                TextUtil.sendMessage(sender, "&cPodane argumenty nie sa liczbami.");
                return;
            }
            p.teleportAsync(new Location(
                    p.getWorld(),
                    x,
                    y,
                    z,
                    p.getLocation().getYaw(),
                    p.getLocation().getPitch()
            ));

            ToolsPlugin.getInstance().getMessageConfiguration().getTeleportedToLocation()
                    .send(p);
            return;
        }
        if (args.length == 4) {
            int x, y, z;

            try {
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
            } catch (NumberFormatException ex) {
                TextUtil.sendMessage(sender, "&cPodane argumenty nie sa liczbami.");
                return;
            }
            World world = ((Player) sender).getWorld();
            Location location = new Location(world, x, y, z);
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                ToolsPlugin.getInstance().getMessageConfiguration().getIncorrectPlayer()
                        .addPlaceholder(ImmutableMultimap.of("[PLAYER]", args[0]))
                        .send((Player) sender);
                return;
            }

            target.teleportAsync(location);
            ToolsPlugin.getInstance().getMessageConfiguration().getTeleportedToLocation()
                    .send(target);
            return;
        }
        return;
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if(args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            if (Bukkit.getPlayer(args[0]) != null) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .toList();
            } else {
                return Collections.singletonList(String.valueOf(player.getLocation().getBlockY()));
            }
        }
        else if (args.length == 3) {
            Location location = player.getTargetBlockExact(100).getLocation();
            if(location == null) return List.of("0");
            return Collections.singletonList(Stream.of(location.getZ()).toList().toString());
        }
        else if(args.length == 4) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());

        else return null;
    }
}
