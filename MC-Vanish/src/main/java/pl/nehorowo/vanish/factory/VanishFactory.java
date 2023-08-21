package pl.nehorowo.vanish.factory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.nehorowo.vanish.VanishPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishFactory {

    @Getter
    private final List<UUID> vanishedPlayers = new ArrayList<>();

    public void changeVanishStatus(Player player) {
        if(vanishedPlayers.contains(player.getUniqueId())) {
            vanishedPlayers.remove(player.getUniqueId());

            Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(VanishPlugin.getInstance(), player));

            VanishPlugin.getInstance().getMessageConfiguration()
                    .getTurnedOffVanish()
                    .send(player);

            player.getWorld().strikeLightningEffect(player.getLocation());
            return;
        }

        vanishedPlayers.add(player.getUniqueId());
        Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(VanishPlugin.getInstance(), player));

        VanishPlugin.getInstance().getMessageConfiguration()
                .getTurnedOnVanish()
                .send(player);

        player.getWorld().strikeLightningEffect(player.getLocation());
    }
}
