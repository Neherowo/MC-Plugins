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
import pl.nehorowo.tools.factory.*;
import pl.nehorowo.tools.listener.*;
import pl.nehorowo.tools.notice.NoticeSerializer;
import pl.nehorowo.tools.task.*;
import pl.nehorowo.tools.utils.TextUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Getter
public final class ToolsPlugin extends JavaPlugin {

    // singleton(s)
    @Getter private static ToolsPlugin instance;

    private TeleportFactory teleportFactory;
    private UserFactory userFactory;
    private CombatFactory combatFactory;
    private ChatFactory chatFactory;
    private ChatBubbleFactory chatBubbleFactory;
    private BanFactory banFactory;

    // configuration files
    private Configuration configuration;
    private MessageConfiguration messageConfiguration;

    @Getter private static InventoryManager invManager;

    @Override
    public void onLoad() {
        instance = this;

        teleportFactory = new TeleportFactory();
        userFactory = new UserFactory();
        combatFactory = new CombatFactory();
        chatFactory = new ChatFactory();
        chatBubbleFactory = new ChatBubbleFactory();
        banFactory = new BanFactory();
    }

    @Override
    public void onEnable() {
        long startupTime = System.currentTimeMillis();

//        TextUtil.sendLogger("&8-------------------------------------------");
//        TextUtil.sendLogger(" ");
//        TextUtil.sendLogger("___  ________     _____           _    ");
//        TextUtil.sendLogger("|  \\/  /  __ \\   |_   _|         | |   ");
//        TextUtil.sendLogger("| .  . | /  \\/_____| | ___   ___ | |___");
//        TextUtil.sendLogger("| |\\/| | |  |______| |/ _ \\ / _ \\| / __|");
//        TextUtil.sendLogger("| |  | | \\__/\\     | | (_) | (_) | \\__ \\");
//        TextUtil.sendLogger("\\_|  |_/\\____/     \\_/\\___/ \\___/|_|___/");
//        TextUtil.sendLogger(" ");
//        TextUtil.sendLogger(" &8** &fLoading NTools by &eNehorowo...");
//        TextUtil.sendLogger(" &8** &fVersion: &e" + getDescription().getVersion());
//        TextUtil.sendLogger(" ");
//        TextUtil.sendLogger(" &8** &fLoading configuration files...");
        configuration = ConfigManager.create(Configuration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/configuration.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
//        TextUtil.sendLogger(" &8** &aSuccessfully loaded file: &2configuration.yml");

        messageConfiguration = ConfigManager.create(MessageConfiguration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/messages.yml");
            it.withSerdesPack(registry -> registry.register(new NoticeSerializer()));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
//        TextUtil.sendLogger(" &8** &aSuccessfully loaded file: &2messages.yml");
//
//        TextUtil.sendLogger(" &8** &fImplementing chat bubbles...");
//        if(!Bukkit.getPluginManager().isPluginEnabled("DecentHolograms")) {
//            TextUtil.sendLogger(" &8• &cDecentHolograms is not installed!");
//            Bukkit.getPluginManager().disablePlugin(this);
//            return;
//        }
//        TextUtil.sendLogger(" &8** &aImplemented chat bubbles!");

        // registering commands, listeners, tasks
//        TextUtil.sendLogger(" &8** &fLoading commands...");
        registerCommands();
//        TextUtil.sendLogger(" &8** &aLoaded commands!");

//        TextUtil.sendLogger(" &8** &fLoading listeners...");
        registerListeners();
//        TextUtil.sendLogger(" &8** &aLoaded listeners!");

//        TextUtil.sendLogger(" &8** &fLoading tasks...");
        registerTasks();
//        TextUtil.sendLogger(" &8** &aLoaded tasks!");

        // initializing menu(s)
//        TextUtil.sendLogger(" &8** &fLoading inventories...");
        invManager = new InventoryManager(this);
        invManager.init();
//        TextUtil.sendLogger(" &8** &aLoaded inventories!");

//        TextUtil.sendLogger(" ");
//        TextUtil.sendLogger(" &aPlugin have been loaded in &2" + (System.currentTimeMillis() - startupTime) + "ms!");
//        TextUtil.sendLogger("&8-------------------------------------------");
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

//                new BanCommand(),
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
        new LoginListener(this);
    }

    private void registerTasks() {
        getServer().getScheduler().runTaskTimer(this, new TeleportTask(this), 0L, 20L);
        getServer().getScheduler().runTaskTimer(this, new WeatherTask(), 0, 20L * 5);

        getServer().getScheduler().runTaskTimerAsynchronously(this, new CombatTask(this), 0, 4);
        getServer().getScheduler().runTaskTimerAsynchronously(this, new CheckTask(this), 0, 20L * 5);
        getServer().getScheduler().runTaskTimerAsynchronously(this, new ChatBubblesTask(this), 5L, 5L);
    }

}
