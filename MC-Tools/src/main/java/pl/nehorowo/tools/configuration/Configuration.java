package pl.nehorowo.tools.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.nehorowo.tools.notice.Notice;
import pl.nehorowo.tools.notice.NoticeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {
    @Comment("MySQL configuration")
    private String host = "localhost";
    private int port = 3306;
    private String database = "database";
    private String username = "username";
    private String password = "password";
    @Comment(" ")
    @Comment("Taki prefix do permisji - np tools. + funkcja, np tools.teleport.vip albo tools.socialspy")
    private String adminPermission = "tools";
    @Comment("W sekundach")
    private int playerTeleportTime = 10;
    private int vipTeleportTime = 8;
    private int svipTeleportTime = 6;
    private int combatTime = 20;

    @Setter private Location spawnLocation = new Location(Bukkit.getWorld("world"), 0, 100, 0);
    @Setter private Location checkLocation = new Location(Bukkit.getWorld("world"), 20, 100, 10);
    @Comment("true = otwiera gui z teleportami, false = teleportuje na od razu na spawn")
    private boolean openSpawnMenu = true;
    private String spawnMenuTitle = "&8** &ePrzeteleportuj się na spawn! &8**";
    private String itemSpawnTitle = "&8** &ePrzeteleportuj się na spawn! &8**";
    @Comment(" ")
    private Material itemSpawnType = Material.DIAMOND;
    private int chatCooldown = 3;
    @Comment(" ")
    private Material itemMessageHistoryType = Material.PAPER;
    private String messageHistoryMenuTitle = "&cWiadomość gracza [PLAYER]";
    private List<String> messageHistoryMenuLore = Arrays.asList(" ", " &8** &fWiadomość: &7[MESSAGE] &8**", " ");
    private String banCommand = "ban [PLAYER] Wykrycie czitow!";
    private String banLogoutCommand = "ban [PLAYER] Logout podczas sprawdzania!";
    private List<String> chatBubbleFormat = Arrays.asList(
            "&fGracz &6[PLAYER]", "&fNapisał: &e[MESSAGE]"
    );
    private String kickPlayerFormat = "&4Zostałeś wyrzucony z serwera!\n&7Powód: &c[REASON]";
    private String banPlayerFormat = "&4Zostales zbanowany!\n&7Powód: &c[REASON]\n&7Czas: &c[TIME]\n&7Zbanowany przez: &c[ADMIN]";
    private String banPlayerRemainingFormat = "&4Zostales zbanowany!\n&7Powód: &c[REASON]\n&7Pozostalo: &c[TIME]\n&7Zbanowany przez: &c[ADMIN]";




}
