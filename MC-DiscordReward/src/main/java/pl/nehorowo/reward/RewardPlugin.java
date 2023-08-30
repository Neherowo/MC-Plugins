package pl.nehorowo.reward;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nehorowo.reward.configuration.BotConfiguration;
import pl.nehorowo.reward.configuration.Configuration;
import pl.nehorowo.reward.configuration.MessageConfiguration;
import pl.nehorowo.reward.controller.UserController;
import pl.nehorowo.reward.database.DatabaseConfiguration;
import pl.nehorowo.reward.database.DatabaseConnector;
import pl.nehorowo.reward.database.serializer.UUIDSerializer;
import pl.nehorowo.reward.database.serializer.UserControllerSerializer;
import pl.nehorowo.reward.listener.QuitListener;
import pl.nehorowo.reward.notice.NoticeSerializer;
import pl.nehorowo.reward.service.BotService;
import pl.nehorowo.reward.service.UserService;

import java.lang.reflect.Field;
import java.util.List;

@Getter
public class RewardPlugin extends JavaPlugin {

    @Getter private static RewardPlugin instance;

    private DatabaseConnector connector;

    private Configuration configuration;
    private MessageConfiguration messageConfiguration;
    private BotConfiguration botConfiguration;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registerConfiguration();

        connector = new DatabaseConnector(new DatabaseConfiguration(
                getConfiguration().getHost(),
                getConfiguration().getUsername(),
                getConfiguration().getPassword(),
                getConfiguration().getDatabase(),
                getConfiguration().getPort(),
                getConfiguration().isSsl()
        ));

        connector.registerSerializer(new UserControllerSerializer());
        connector.registerSerializer(new UUIDSerializer());
        connector.registerDataObjectToScan(UserController.class);

        connector.getScanner(UserController.class)
                .ifPresent(userControllerDataObjectScanner ->
                        userControllerDataObjectScanner.load(UserService.getInstance())
                );

        registerCommands();
        registerListeners();
        registerTasks();

        BotService.getInstance().setupBot();
    }

    private void registerListeners() {
        List.of(
                new QuitListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    @SneakyThrows
    private void registerCommands() {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(getServer());

//        List.of(
//        ).forEach(commands ->
//                commandMap.register("mc-reward", commands)
//        );
    }

    private void registerTasks() {

    }

    private void registerConfiguration() {
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

        botConfiguration = ConfigManager.create(BotConfiguration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(this.getDataFolder() + "/bot-configuration.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }
}
