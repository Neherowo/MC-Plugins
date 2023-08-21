package pl.nehorowo.rtp.factory;

import com.google.common.collect.ImmutableMultimap;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import pl.nehorowo.rtp.RtpPlugin;
import pl.nehorowo.rtp.utils.Util;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RtpFactory {

    @Getter private final ConcurrentMap<UUID, Long> cooldown = new ConcurrentHashMap<>();

    public void randomTeleport(Player player, int distance, World world) {
        Util.runAsync(RtpPlugin.getInstance(), () -> {
            RtpPlugin.getInstance().getMessageConfiguration()
                    .getTeleporting()
                    .send(player);

            Random ran = new Random();
            if (world.getEnvironment() != World.Environment.NORMAL) return;

            if(!player.getWorld().getName().equals(RtpPlugin.getInstance().getConfiguration().getAllowedWorld())) {
                RtpPlugin.getInstance().getMessageConfiguration()
                        .getWrongWorld()
                        .send(player);
                return;
            }

            Location safeLoc = getSafeLoc(ran, distance, world);
            if(getCooldown().containsKey(player.getUniqueId()) && System.currentTimeMillis() > getCooldown().get(player.getUniqueId())) {
                Long time = getCooldown().get(player.getUniqueId());
                time = (time - System.currentTimeMillis() / 1000L);

                int finalTime = Math.round(time);

                RtpPlugin.getInstance().getMessageConfiguration()
                        .getCooldown()
                        .addPlaceholder(ImmutableMultimap.of("[TIME]", String.valueOf(finalTime)))
                        .send(player);
                return;
            }
            if (safeLoc != null) {
                Util.runSync(RtpPlugin.getInstance(), () -> {
                    player.teleportAsync(safeLoc);

                    RtpPlugin.getInstance().getMessageConfiguration()
                            .getSuccessfullyTeleported()
                            .send(player);

                    if(distance == RtpPlugin.getInstance().getConfiguration().getPlayerDistance()) getCooldown().put(player.getUniqueId(), System.currentTimeMillis() + (1000L * RtpPlugin.getInstance().getConfiguration().getPlayerDistance()));
                    else if(distance == RtpPlugin.getInstance().getConfiguration().getVipDistance()) getCooldown().put(player.getUniqueId(), System.currentTimeMillis() + (1000L * RtpPlugin.getInstance().getConfiguration().getVipDistance()));
                    else if(distance == RtpPlugin.getInstance().getConfiguration().getSvipDistance()) getCooldown().put(player.getUniqueId(), System.currentTimeMillis() + (1000L * RtpPlugin.getInstance().getConfiguration().getSvipDistance()));
                    else System.out.println("Wystapil nieoczekiwany blad! Przy " + player.getName() + " wykonywaniu Teleportowania... #1");
                });
            }
            else {
                RtpPlugin.getInstance().getMessageConfiguration()
                                .getTryAgain()
                                .send(player);
                System.out.println("Wystapil nieoczekiwany blad! Przy " + player.getName() + " wykonywaniu Teleportowania... #2");
            }
        });
    }

    public static Location getSafeLoc(Random ran, Integer distance, World world) {
        for (int i = 0; i < RtpPlugin.getInstance().getConfiguration().getMaxTries(); i++) {
            int x = Util.getRandomInt(ran, 0, distance);
            int z = Util.getRandomInt(ran, 0, distance);

            Block block = world.getHighestBlockAt(x, z);
            int y = block.getY() + 1;

            Location to = new Location(world, x, y, z);

            if (block.getType() != Material.LAVA &&
                    block.getType() != Material.WATER &&
                    block.getType() != Material.COBWEB &&
                    !block.getType().toString().contains("LEAVES")) {
                Block tob = to.getBlock();
                if (tob.getRelative(BlockFace.EAST, 1).getType() == Material.AIR && tob
                        .getRelative(BlockFace.NORTH, 1).getType() == Material.AIR && tob
                        .getRelative(BlockFace.SOUTH, 1).getType() == Material.AIR && tob
                        .getRelative(BlockFace.WEST, 1).getType() == Material.AIR)
                    return to;
            }
        }
        return null;
    }

}
