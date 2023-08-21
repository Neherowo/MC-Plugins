package pl.nehorowo.rtp;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nehorowo.rtp.command.*;
import pl.nehorowo.rtp.configuration.Configuration;
import pl.nehorowo.rtp.configuration.MessageConfiguration;
import pl.nehorowo.rtp.factory.RtpFactory;
import pl.nehorowo.rtp.notice.NoticeSerializer;

import java.lang.reflect.Field;
import java.util.List;

@Getter
public class RtpPlugin extends JavaPlugin {

    @Getter private static RtpPlugin instance;
    @Getter private static InventoryManager invManager;

    private Configuration configuration;
    private MessageConfiguration messageConfiguration;

    private RtpFactory rtpFactory;

    @Override
    public void onLoad() {
        instance = this;

        rtpFactory = new RtpFactory();
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
        registerListeners();

        invManager = new InventoryManager(this);
        invManager.init();
    }

    @SneakyThrows
    private void registerCommands() {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(getServer());

        List.of(
                new RtpCommand()
        ).forEach(commands ->
                commandMap.register("tools", commands)
        );
    }

    private void registerListeners() {
    }

}
