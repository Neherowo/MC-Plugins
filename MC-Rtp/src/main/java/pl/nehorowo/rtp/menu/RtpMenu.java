package pl.nehorowo.rtp.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import pl.nehorowo.rtp.RtpPlugin;
import pl.nehorowo.rtp.utils.ItemBuilder;
import pl.nehorowo.rtp.utils.TextUtil;
import pl.nehorowo.rtp.utils.Util;

public class RtpMenu implements InventoryProvider {

    private final RtpPlugin plugin = RtpPlugin.getInstance();
    
    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(Util.calcSpace(plugin.getConfiguration().getPlayerSlot()), ClickableItem.of(
                new ItemBuilder(plugin.getConfiguration().getPlayerItemMaterial(), 1)
                        .setName(plugin.getConfiguration().getPlayerItemTitle())
                        .addLore(plugin.getConfiguration().getPlayerItemLore())
                            .addLorePlaceholder("[COOLDOWN]", String.valueOf(plugin.getConfiguration().getPlayerCooldown()))
                            .addLorePlaceholder("[DISTANCE]", String.valueOf(plugin.getConfiguration().getPlayerDistance()))
                        .build(),
                true,
                e -> {
                    plugin.getRtpFactory().randomTeleport(
                            player,
                            plugin.getConfiguration().getPlayerDistance(),
                            player.getWorld()
                    );

                    player.closeInventory();
            })
        );

        contents.set(Util.calcSpace(plugin.getConfiguration().getVipSlot()), ClickableItem.of(
                new ItemBuilder(plugin.getConfiguration().getVipItemMaterial(), 1)
                            .setName(plugin.getConfiguration().getVipItemTitle())
                            .addLore(plugin.getConfiguration().getVipItemLore())
                            .addLorePlaceholder("[COOLDOWN]", String.valueOf(plugin.getConfiguration().getVipCooldown()))
                            .addLorePlaceholder("[DISTANCE]", String.valueOf(plugin.getConfiguration().getVipDistance()))
                        .build(),
                true,
                e -> {
                    plugin.getRtpFactory().randomTeleport(
                            player,
                            plugin.getConfiguration().getVipDistance(),
                            player.getWorld()
                    );

                    player.closeInventory();
                })
        );


        contents.set(Util.calcSpace(plugin.getConfiguration().getSvipSlot()), ClickableItem.of(
                new ItemBuilder(plugin.getConfiguration().getSvipItemMaterial(), 1)
                        .setName(plugin.getConfiguration().getSvipItemTitle())
                        .addLore(plugin.getConfiguration().getSvipItemLore())
                            .addLorePlaceholder("[COOLDOWN]", String.valueOf(plugin.getConfiguration().getSvipCooldown()))
                            .addLorePlaceholder("[DISTANCE]", String.valueOf(plugin.getConfiguration().getSvipDistance()))
                        .build(),

                true,
                e -> {
                    plugin.getRtpFactory().randomTeleport(
                            player,
                            plugin.getConfiguration().getSvipDistance(),
                            player.getWorld()
                    );

                    player.closeInventory();
                })
        );


    }

    public void openRtpMenu(Player player) {
        SmartInventory.builder()
                .provider(this)
                .size(3, 9)
                .title(TextUtil.fixColor(plugin.getConfiguration().getInventoryMenuTitle()))
                .build()
                .open(player);

    }
}
