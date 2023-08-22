package pl.nehorowo.tools.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.utils.ItemBuilder;
import pl.nehorowo.tools.utils.TextUtil;

public class ChatMenu implements InventoryProvider {

    private final ToolsPlugin plugin = ToolsPlugin.getInstance();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(1, 2,
                ClickableItem.of(new ItemBuilder(Material.GREEN_DYE)
                                .setName("&aWłącz czat!")
                                .build(),
                        false,
                        e -> {
                            plugin.getChatController().changeChatStatus(player, true);
                            player.closeInventory();
                        })
        );

        contents.set(1, 4,
                ClickableItem.of(new ItemBuilder(Material.WHITE_DYE)
                                .setName("&fWyczyść czat!")
                                .build(),
                        false,
                        e -> {
                            plugin.getChatController().clearChat(player);
                            player.closeInventory();
                        })
        );

        contents.set(1, 6,
                ClickableItem.of(new ItemBuilder(Material.RED_DYE)
                                .setName("&cWyłącz czat!")
                                .build(),
                        false,
                        e -> {
                            plugin.getChatController().changeChatStatus(player, false);
                            player.closeInventory();
                        })
        );

    }


    public void openChatMenu(Player player) {
        SmartInventory.builder()
                .provider(this)
                .size(3, 9)
                .title(TextUtil.fixColor("&cZarządzanie czatem"))
                .build()
                .open(player);
    }
}
