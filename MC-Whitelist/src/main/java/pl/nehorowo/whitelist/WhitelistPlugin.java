package pl.nehorowo.whitelist;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nehorowo.whitelist.command.WhitelistCommand;
import pl.nehorowo.whitelist.configuration.Configuration;
import pl.nehorowo.whitelist.configuration.MessageConfiguration;
import pl.nehorowo.whitelist.configuration.WhitelistPeopleConfiguration;
import pl.nehorowo.whitelist.listener.LoginListener;
import pl.nehorowo.whitelist.notice.NoticeSerializer;
import pl.nehorowo.whitelist.serializer.ItemMenuSerializer;
import pl.nehorowo.whitelist.service.WhitelistService;
import pl.nehorowo.whitelist.util.TextUtil;

import java.lang.reflect.Field;
import java.util.List;

@Getter
public class WhitelistPlugin extends JavaPlugin {

    @Getter private static WhitelistPlugin instance;

    private Configuration configuration;
    private MessageConfiguration messageConfiguration;
    private WhitelistPeopleConfiguration whitelistPeopleConfiguration;

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

        whitelistPeopleConfiguration = ConfigManager.create(WhitelistPeopleConfiguration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/whitelist-people.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        inventoryProvider = new InventoryManager(this);
        inventoryProvider.init();

        int i = WhitelistService.getInstance().injectWhitelist();
        TextUtil.sendLogger("Załadowano " + i + " graczy do whitelisty!");

        registerCommands();
        registerListeners();
    }


    @SneakyThrows
    private void registerCommands() {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(getServer());

        List.of(
                new WhitelistCommand()
        ).forEach(commands ->
                commandMap.register("mc-whitelist", commands)
        );
    }

    private void registerListeners() {
        new LoginListener(this);
    }
}
