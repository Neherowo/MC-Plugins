package pl.nehorowo.vanish.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;

@Getter
public class Configuration extends OkaeriConfig {
    private String permissionPrefix = "vanish";
}
