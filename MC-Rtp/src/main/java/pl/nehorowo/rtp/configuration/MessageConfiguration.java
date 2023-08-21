package pl.nehorowo.rtp.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import pl.nehorowo.rtp.notice.Notice;
import pl.nehorowo.rtp.notice.NoticeType;

@Getter

@Header("Plik konfiguracyjny messages.yml")
@Header("")
@Header(" Wszystkie wiadomości w pluginie są konfigurowalne.")
@Header(" Plugin wspiera HEX kolory. Ich format to: &#0000ff (gdzie 0000ff to kolor w HEX).")
@Header(" Dziękuje za uzywanie pluginu.")
public class MessageConfiguration extends OkaeriConfig {

    private Notice noPermission = new Notice(NoticeType.MESSAGE, "&cNie posiadasz uprawnień do tej komendy.");
    private Notice correctUsage = new Notice(NoticeType.MESSAGE, "&cPoprawne użycie: &7[USAGE]");
    private Notice reloaded = new Notice(NoticeType.MESSAGE, "&aPomyślnie przeładowano konfigurację.");

    private Notice teleporting = new Notice(NoticeType.SUBTITLE, "&aTrwa szukanie bezpiecznej lokalizacji...");
    private Notice wrongWorld = new Notice(NoticeType.SUBTITLE, "&cPodany świat jest zablokowany!");
    private Notice cooldown = new Notice(NoticeType.SUBTITLE, "&cMusisz odczekać jeszcze &4[TIME] &csekund.");
    private Notice tryAgain = new Notice(NoticeType.SUBTITLE, "&cNie udało się znaleźć bezpiecznej lokalizacji. Spróbuj ponownie.");
    private Notice successfullyTeleported = new Notice(NoticeType.SUBTITLE, "&aZostałeś pomyślnie przeteleportowany!");


}
