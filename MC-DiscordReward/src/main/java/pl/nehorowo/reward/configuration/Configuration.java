package pl.nehorowo.reward.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {

    @Comment("Prefix permissji pluginu, np tools + . + komenda/funkcja - np. tools.rtp")
    private String permissionPrefix = "reward";
    private String host = "localhost";
    private int port = 3306;
    private String database = "database";
    private String username = "username";
    private String password = "password";
    private boolean ssl = false;

    private List<String> rewardCommands = List.of(
            "give [PLAYER] diamond 1",
            "say [PLAYER] otrzymal nagrode!"
    );
}
