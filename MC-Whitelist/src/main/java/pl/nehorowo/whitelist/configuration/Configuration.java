package pl.nehorowo.whitelist.configuration;

import eu.decentsoftware.holograms.api.utils.items.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.nehorowo.whitelist.util.TextUtil;

import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {

    private String permissionPrefix = "whitelist";
//    private String notWhitelistedMessage = " " +
//            "&cNie znajdujesz się na białej liście!\n" +
//            "&cNa serwerze znajduje się aktualnie prace techniczne.\n" +
//            "\n" +
//            "&cZapraszamy po pracy technicznej!" +
//            " ";
    private String notWhitelistedMessage = """
         &cNie znajdujesz się na białej liście!
        &cNa serwerze znajduje się aktualnie prace techniczne.

        &cZapraszamy po pracy technicznej!\s""";
    private String whitelistTitle = "&cLista osob na bialej liscie";

    private String whitelistItemTitle = "&c[PLAYER]";
    private List<String> whitelistItemLore = Arrays.asList(
            " ",
            "&7Kliknij aby usunac z bialej listy",
            " "
    );
}
