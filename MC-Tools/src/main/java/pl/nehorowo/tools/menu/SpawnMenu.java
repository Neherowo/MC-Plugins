package pl.nehorowo.tools.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.utils.ItemBuilder;
import pl.nehorowo.tools.utils.RandomUtil;
import pl.nehorowo.tools.utils.TextUtil;

public class SpawnMenu implements InventoryProvider {

    private static final ToolsPlugin plugin = ToolsPlugin.getInstance();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(RandomUtil.getRandomInt(1, 3), RandomUtil.getRandomInt(1, 9),
            ClickableItem.of(new ItemBuilder(plugin.getConfiguration().getItemSpawnType(), 1)
                .setName(TextUtil.fixColor(plugin.getConfiguration().getSpawnMenuTitle()))
                .build(),
                false,
                e -> {
                    plugin.getTeleportFactory()
                            .addTeleport(
                                    player,
                                    plugin.getConfiguration().getSpawnLocation()
                            );
                    player.closeInventory();
            })
        );

    }

    public void openSpawnMenu(Player player) {
        if(plugin.getConfiguration().isOpenSpawnMenu())
            SmartInventory.builder()
                .provider(this)
                .size(3, 9)
                .title(TextUtil.fixColor(plugin.getConfiguration().getSpawnMenuTitle()))
                .build()
                .open(player);
        else {
            plugin.getTeleportFactory()
                    .addTeleport(
                            player,
                            plugin.getConfiguration().getSpawnLocation()
                    );
        }
    }
}
