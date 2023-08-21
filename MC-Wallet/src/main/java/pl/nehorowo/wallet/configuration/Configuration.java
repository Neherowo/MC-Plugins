package pl.nehorowo.wallet.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.nehorowo.wallet.builder.ItemBuilder;
import pl.nehorowo.wallet.util.TextUtil;

import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {

    @Comment("Prefix permissji pluginu, np tools + . + komenda/funkcja - np. tools.rtp")
    private String permissionPrefix = "wallet";
    private String servicesMenuTitle = "&aZakup uslugi!";
}
