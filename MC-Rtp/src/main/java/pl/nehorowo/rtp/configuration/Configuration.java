package pl.nehorowo.rtp.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {

    @Comment("Prefix permissji pluginu, np tools + . + komenda/funkcja - np. tools.rtp")
    private String permissionPrefix = "tools";
    private String inventoryMenuTitle = "&cLosowy teleport";
    private String allowedWorld = "world";
    private int maxTries = 10;
    @Comment("W sekundach")
    private int playerCooldown = 20;
    private int vipCooldown = 40;
    private int svipCooldown = 60;
    @Comment("W kratkach")
    private int playerDistance = 1000;
    private int vipDistance = 2000;
    private int svipDistance = 5000;
    @Comment("Slot w gui (od 0)")
    private int playerSlot = 10;
    private int vipSlot = 13;
    private int svipSlot = 16;
    @Comment("Ustawienie GUI RTP")
    private Material playerItemMaterial = Material.WOODEN_SHOVEL;
    private String playerItemTitle = "&eLosowa teleportacja - GRACZ";
    private List<String> playerItemLore = Arrays.asList(
            " ",
            " &8** &fKliknij aby sie przeteleportowac",
            " &8** &fCooldown: &c[COOLDOWN] sekund",
            " &8** &fOdleglosc: &c[DISTANCE] kratek",
            " "
    );
    private Material vipItemMaterial = Material.GOLDEN_SWORD;
    private String vipItemTitle = "&eLosowa teleportacja - VIP";
    private List<String> vipItemLore = Arrays.asList(
            " ",
            " &8** &fKliknij aby sie przeteleportowac",
            " &8** &fCooldown: &c[COOLDOWN] sekund",
            " &8** &fOdleglosc: &c[DISTANCE] kratek",
            " "
    );
    private Material svipItemMaterial = Material.DIAMOND_SWORD;
    private String svipItemTitle = "&eLosowa teleportacja - SVIP";
    private List<String> svipItemLore = Arrays.asList(
            " ",
            " &8** &fKliknij aby sie przeteleportowac",
            " &8** &fCooldown: &c[COOLDOWN] sekund",
            " &8** &fOdleglosc: &c[DISTANCE] kratek",
            " "
    );
}
