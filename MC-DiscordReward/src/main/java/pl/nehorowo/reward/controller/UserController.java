package pl.nehorowo.reward.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import pl.nehorowo.reward.RewardPlugin;
import pl.nehorowo.reward.database.api.DataUpdater;
import pl.nehorowo.reward.database.api.stereotype.DataObject;
import pl.nehorowo.reward.database.api.stereotype.PrimaryKey;
import pl.nehorowo.reward.database.api.stereotype.Value;
import pl.nehorowo.reward.service.UpdateService;

import java.sql.ResultSet;
import java.util.UUID;

@DataObject(table = "rewardUser") @Getter
@Setter
public class UserController implements DataUpdater {

    @PrimaryKey(value = @Value(key = "uuid", type = "VARCHAR(64)"))
    private final UUID uuid;

    @Value(key = "name", type = "VARCHAR(32)")
    private String name;

    @Value(key = "claimedReward", type ="VARCHAR(32)")
    private boolean claimedReward;

    @Value(key = "discordId", type = "VARCHAR(64)")
    private long discordId;

    private Player player;


    public UserController(UUID uuid) {
        this.uuid = uuid;
    }

    @SneakyThrows
    public UserController(ResultSet resultSet){
        this.uuid = UUID.fromString(resultSet.getString("uuid"));
        this.name = resultSet.getString("name");
        this.claimedReward = resultSet.getBoolean("claimedReward");
        this.discordId = resultSet.getLong("discordId");
    }

    @Override
    public void update() {
        RewardPlugin.getInstance().getConnector().getScanner(UserController.class)
                .ifPresentOrElse(userControllerDataObjectScanner ->
                                userControllerDataObjectScanner.update(this)
                        , () -> System.out.println("UserController scanner doesnt  not exists."));

        UpdateService.getInstance().getUserControllerSet()
                .remove(this);
    }
}

