package pl.nehorowo.treasuremap.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;

@Getter
public class Configuration extends OkaeriConfig {

    @Comment("Prefix do uprawnien - np treasuremap + . + komenda/funkcja")
    private String permissionPrefix = "treasuremap";
}
