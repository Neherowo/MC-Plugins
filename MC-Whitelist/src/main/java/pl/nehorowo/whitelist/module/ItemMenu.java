package pl.nehorowo.whitelist.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter@AllArgsConstructor@Setter
public class ItemMenu {

    private ItemStack item;
    private int slot;
}
