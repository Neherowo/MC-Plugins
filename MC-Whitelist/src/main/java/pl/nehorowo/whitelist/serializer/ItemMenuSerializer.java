package pl.nehorowo.whitelist.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import pl.nehorowo.whitelist.module.ItemMenu;

public class ItemMenuSerializer implements ObjectSerializer<ItemMenu> {
    public boolean supports(Class<? super ItemMenu> type) {
        return ItemMenu.class.isAssignableFrom(type);
    }

    public void serialize(@NonNull ItemMenu object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("item", object.getItem());
        data.add("slot", object.getSlot());
    }

    public ItemMenu deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new ItemMenu(
                data.get("item", ItemStack.class),
                data.get("slot", Integer.class)
        );
    }
}