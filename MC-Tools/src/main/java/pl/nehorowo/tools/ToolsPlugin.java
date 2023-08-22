package pl.nehorowo.tools;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nehorowo.tools.command.*;
import pl.nehorowo.tools.configuration.Configuration;
import pl.nehorowo.tools.configuration.MessageConfiguration;
import pl.nehorowo.tools.database.DatabaseConfiguration;
import pl.nehorowo.tools.database.DatabaseConnector;
import pl.nehorowo.tools.database.serializer.UUIDSerializer;
import pl.nehorowo.tools.database.serializer.UserControllerSerializer;
import pl.nehorowo.tools.controller.*;
import pl.nehorowo.tools.listener.*;
import pl.nehorowo.tools.notice.NoticeSerializer;
import pl.nehorowo.tools.service.UserService;
import pl.nehorowo.tools.task.*;

import java.lang.reflect.Field;
import java.util.List;

@Getter
public final class ToolsPlugin extends JavaPlugin {

    // singleton(s)
    @Getter private static ToolsPlugin instance;

    private TeleportController teleportController;
    private CombatController combatController;
    private ChatController chatController;
    private ChatBubbleController chatBubbleController;

    // configuration files
    private Configuration configuration;
    private MessageConfiguration messageConfiguration;

    @Getter private static InventoryManager invManager;
    private DatabaseConnector connector;

    @Override
    public void onLoad() {
        instance = this;

        teleportController = new TeleportController();
        combatController = new CombatController();
        chatController = new ChatController();
        chatBubbleController = new ChatBubbleController();
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

        invManager = new InventoryManager(this);
        invManager.init();
    }


    @SneakyThrows
    private void registerCommands() {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(getServer());
        List.of(
                new MsgCommand(),
                new ReplyCommand(),
                new SocialSpyCommand(),

                new ToolsCommand(),

                new TpacceptCommand(),
                new TpaCommand(),
                new TpCommand(),

                new GamemodeCommand(),

                new SpawnCommand(),
                new SetSpawnCommand(),

                new ChatCommand(),

                new GiveCommand(),

                new HealCommand(),
                new FeedCommand(),

                new BroadcastCommand(),

                new ListCommand(),

                new CheckCommand(),

                new KickCommand()
        ).forEach(commands ->
                commandMap.register("tools", commands)
        );
    }

    private void registerListeners() {
        new JoinQuitListener(this);
        new CombatListener(this);
        new ChatListener(this);
        new MoveListener(this);
    }

    private void registerTasks() {
        getServer().getScheduler().runTaskTimer(this, new TeleportTask(this), 0L, 20L);
        getServer().getScheduler().runTaskTimer(this, new WeatherTask(), 0, 20L * 5);

        getServer().getScheduler().runTaskTimerAsynchronously(this, new CombatTask(this), 0, 4);
        getServer().getScheduler().runTaskTimerAsynchronously(this, new CheckTask(this), 0, 20L * 5);
        getServer().getScheduler().runTaskTimerAsynchronously(this, new ChatBubblesTask(this), 5L, 5L);
    }

}
