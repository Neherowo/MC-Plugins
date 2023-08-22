package pl.nehorowo.tools.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.database.api.DataUpdater;
import pl.nehorowo.tools.database.api.stereotype.DataObject;
import pl.nehorowo.tools.database.api.stereotype.PrimaryKey;
import pl.nehorowo.tools.database.api.stereotype.Value;
import pl.nehorowo.tools.service.UpdateService;
import pl.nehorowo.tools.module.ChatBubble;
import pl.nehorowo.tools.module.Check;

import java.sql.ResultSet;
import java.util.*;

@DataObject(table = "tUsers") @Getter@Setter
public class UserController implements DataUpdater {

    @PrimaryKey(value = @Value(key = "uuid", type = "VARCHAR(64)"))
    private final UUID uuid;

    @Value(key = "name", type = "VARCHAR(32)")
    private String name;

    @Value(key = "socialspy", type ="VARCHAR(32)")
    private boolean socialSpy;


    private ChatBubble chatBubble;
    private Check check = new Check(false, null);

    private List<String> messages = new ArrayList<>();
    private List<Player> tpList = new ArrayList<>();

    private Player lastMsg;
    private Player player;
    private int teleportDelay;
    private long lastMessage;

    public UserController(UUID uuid) {
        this.uuid = uuid;
    }

    @SneakyThrows
    public UserController(ResultSet resultSet){
        this.uuid = UUID.fromString(resultSet.getString("uuid"));
        this.name = resultSet.getString("name");
        this.socialSpy = Boolean.getBoolean(resultSet.getString("socialspy"));
    }

    @Override
    public void update() {
        ToolsPlugin.getInstance().getConnector().getScanner(UserController.class)
                .ifPresentOrElse(userControllerDataObjectScanner ->
                                userControllerDataObjectScanner.update(this)
                        , () -> System.out.println("UserController scanner doesnt  not exists."));

        UpdateService.getInstance().getUserControllerSet()
                .remove(this);
    }
}





