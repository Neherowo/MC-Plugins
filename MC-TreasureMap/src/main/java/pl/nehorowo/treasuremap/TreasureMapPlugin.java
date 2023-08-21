package pl.nehorowo.treasuremap;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nehorowo.treasuremap.configuration.Configuration;
import pl.nehorowo.treasuremap.configuration.MessageConfiguration;
import pl.nehorowo.treasuremap.factory.TreasureFactory;
import pl.nehorowo.treasuremap.notice.NoticeSerializer;

@Getter
public class TreasureMapPlugin extends JavaPlugin {


    @Getter private static TreasureMapPlugin instance;

    private TreasureFactory treasureFactory;

    private Configuration configuration;
    private MessageConfiguration messageConfiguration;


    @Override
    public void onLoad() {
        instance = this;

        treasureFactory = new TreasureFactory();
    }

    @Override
    public void onEnable() {
        configuration = ConfigManager.create(Configuration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/configuration.yml");
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
    }
}
