package pl.nehorowo.whitelist.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

import java.util.Set;

@Getter
public class WhitelistPeopleConfiguration extends OkaeriConfig {

    private Set<String> whitelistPeople = Set.of("Nehorowo");
}
