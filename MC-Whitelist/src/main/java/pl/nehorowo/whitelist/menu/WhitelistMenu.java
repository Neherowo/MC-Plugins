package pl.nehorowo.whitelist.menu;

import eu.decentsoftware.holograms.api.utils.items.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.nehorowo.whitelist.WhitelistPlugin;
import pl.nehorowo.whitelist.service.WhitelistService;
import pl.nehorowo.whitelist.util.TextUtil;

public class WhitelistMenu implements InventoryProvider {


    @Override
    public void init(Player player, InventoryContents contents) {
        System.out.println("init");

        WhitelistService.getInstance().getWhitelistSet().forEach(mess -> System.out.println(mess));

        WhitelistService.getInstance().getWhitelistSet().forEach(name -> {
            contents.add(ClickableItem.of(
                    new ItemBuilder(Material.PLAYER_HEAD)
                            .withName(TextUtil.fixColor(
                                    WhitelistPlugin.getInstance().getConfiguration().getWhitelistItemTitle().replace("[PLAYER]", name)
                            ))
                            .withLore(TextUtil.fixColor(
                                    WhitelistPlugin.getInstance().getConfiguration().getWhitelistItemLore()
                            ))
                            .toItemStack(),
                    false,
                    e -> {
                        WhitelistService.getInstance().removePlayer(name, player);
                        player.closeInventory();
                    }
            ));
        });

    }

    public void open(Player player) {
        SmartInventory.builder()
                .provider(this)
                .size(3, 9)
                .title(TextUtil.fixColor(WhitelistPlugin.getInstance().getConfiguration().getWhitelistTitle()))
                .build()
                .open(player);

    }
}
