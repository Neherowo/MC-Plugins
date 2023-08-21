package pl.nehorowo.wallet.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.nehorowo.wallet.builder.ItemBuilder;
import pl.nehorowo.wallet.module.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
public class ServiceItemsConfiguration extends OkaeriConfig {

    private Set<Service> services = Set.of(
            new Service(
                    new ItemBuilder(Material.GOLD_INGOT, 1)
                            .setName("&6VIP")
                            .addLore(Arrays.asList("&7Kliknij tutaj, aby zakupić range &evip&f za nasza walute!", "&7Koszt: &e10 wPLN"))
                            .build(),
                    13,
                    10,
                    "pex user [PLAYER] group set vip"
                ),
            new Service(
                    new ItemBuilder(Material.GOLD_INGOT, 2)
                            .setName("&6SVIP")
                            .addLore(Arrays.asList("&7Kliknij tutaj, aby zakupić range &eSvip&f za nasza walute!", "&7Koszt: &e10 wPLN"))
                            .build(),
                    15,
                    10,
                    "pex user [PLAYER] group set svip"
            )
        );

}
