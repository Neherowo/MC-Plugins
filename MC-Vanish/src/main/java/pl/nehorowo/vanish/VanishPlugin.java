package pl.nehorowo.vanish;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nehorowo.vanish.command.VanishCommand;
import pl.nehorowo.vanish.configuration.Configuration;
import pl.nehorowo.vanish.configuration.MessageConfiguration;
import pl.nehorowo.vanish.factory.VanishFactory;
import pl.nehorowo.vanish.listener.JoinQuitListener;
import pl.nehorowo.vanish.notice.NoticeSerializer;
import pl.nehorowo.vanish.task.VanishTask;

import java.lang.reflect.Field;
import java.util.List;

@Getter
public class VanishPlugin extends JavaPlugin {

    @Getter private static VanishPlugin instance;

    private Configuration configuration;
    private MessageConfiguration messageConfiguration;

    private VanishFactory vanishFactory;

    @Override
    public void onLoad() {
        instance = this;

        vanishFactory = new VanishFactory();
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

        registerCommands();
        registerTasks();
        registerListeners();
    }


    @SneakyThrows
    private void registerCommands() {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(getServer());

        List.of(
                new VanishCommand()
        ).forEach(commands ->
                commandMap.register("tools", commands)
        );
    }

    private void registerTasks() {
        Bukkit.getScheduler().runTaskTimer(
                this,
                new VanishTask(this),
                0L, 20L
        );
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(
                new JoinQuitListener(this),
                this
        );
    }
}
