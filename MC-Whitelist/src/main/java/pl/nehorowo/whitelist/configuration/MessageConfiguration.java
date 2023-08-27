package pl.nehorowo.whitelist.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;

@Getter

@Header("Plik konfiguracyjny messages.yml")
@Header("")
@Header(" Wszystkie wiadomości w pluginie są konfigurowalne.")
@Header(" Plugin wspiera HEX kolory. Ich format to: &#0000ff (gdzie 0000ff to kolor w HEX).")
@Header(" Dziękuje za uzywanie pluginu.")
public class MessageConfiguration extends OkaeriConfig {

}
