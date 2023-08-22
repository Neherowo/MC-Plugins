package pl.nehorowo.tools.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.utils.ItemBuilder;
import pl.nehorowo.tools.utils.RandomUtil;
import pl.nehorowo.tools.utils.TextUtil;

public class MessageHistoryMenu implements InventoryProvider {

    private static final ToolsPlugin plugin = ToolsPlugin.getInstance();

    @Override
    public void init(Player player, InventoryContents contents) {
        String title = contents.inventory().getTitle();
        if(title == null) return;

        String[] split = title.split(" ");
        String targetName = split[3];

        Player target = Bukkit.getPlayer(targetName);
        UserService.getInstance().get(target.getUniqueId()).ifPresent(user -> {
            user.getMessages().forEach(messages ->
                    contents.add(ClickableItem.of(new ItemBuilder(plugin.getConfiguration().getItemMessageHistoryType(), 1)
                                    .setName(plugin.getConfiguration().getMessageHistoryMenuTitle().replace("[PLAYER]", targetName))
                                    .addLore(plugin.getConfiguration().getMessageHistoryMenuLore())
                                    .addLorePlaceholder("[MESSAGE]", messages)
                                    .build(),
                            false,
                            e -> player.closeInventory()
                    )));
        });
    }

    public void openMessageHistoryMenu(Player player, Player target) {
        SmartInventory.builder()
                .provider(this)
                .size(5, 9)
                .title(TextUtil.fixColor("&fHistoria wiadomosci gracza&e " + target.getName()))
                .build()
                .open(player);

    }
}
