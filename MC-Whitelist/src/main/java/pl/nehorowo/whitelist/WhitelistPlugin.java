package pl.nehorowo.whitelist;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nehorowo.whitelist.configuration.Configuration;
import pl.nehorowo.whitelist.configuration.MessageConfiguration;
import pl.nehorowo.whitelist.notice.NoticeSerializer;
import pl.nehorowo.whitelist.serializer.ItemMenuSerializer;

@Getter
public class WhitelistPlugin extends JavaPlugin {

    @Getter private static WhitelistPlugin instance;

    private Configuration configuration;
    private MessageConfiguration messageConfiguration;

    private InventoryManager inventoryProvider;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        configuration = ConfigManager.create(Configuration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/configuration.yml");
            it.withSerdesPack(registry -> registry.register(new ItemMenuSerializer()));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        messageConfiguration = ConfigManager.create(MessageConfiguration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/messages.yml");
            it.withSerdesPack(registry -> registry.register(new NoticeSerializer()));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        inventoryProvider = new InventoryManager(this);
        inventoryProvider.init();
    }
}
