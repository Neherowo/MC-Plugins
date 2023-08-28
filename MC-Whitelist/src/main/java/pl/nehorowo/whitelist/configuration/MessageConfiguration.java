package pl.nehorowo.whitelist.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import pl.nehorowo.whitelist.notice.Notice;
import pl.nehorowo.whitelist.notice.NoticeType;

@Getter

@Header("Plik konfiguracyjny messages.yml")
@Header("")
@Header(" Wszystkie wiadomości w pluginie są konfigurowalne.")
@Header(" Plugin wspiera HEX kolory. Ich format to: &#0000ff (gdzie 0000ff to kolor w HEX).")
@Header(" Dziękuje za uzywanie pluginu.")
public class MessageConfiguration extends OkaeriConfig {


    private Notice correctUsage = new Notice(NoticeType.MESSAGE, "&cPoprawne uzycie: &4[USAGE]");
    private Notice noPermission = new Notice(NoticeType.MESSAGE, "&cNie posiadasz uprawnień do wykonania tej komendy!");

    private Notice whitelistListFormat = new Notice(NoticeType.MESSAGE, "&fLista graczy na whiteliscie: &c[WHITELIST]");

    private Notice whitelistAdd = new Notice(NoticeType.MESSAGE, "&cDodano gracza &4[NICK] &cdo whitelisty!");
    private Notice whitelistRemove = new Notice(NoticeType.MESSAGE, "&cUsunieto gracza &4[NICK] &cdo whitelisty!");
    private Notice whitelistStatus = new Notice(NoticeType.MESSAGE, "&fStatus whitelisty: [STATUS]");

    private Notice whitelistReload = new Notice(NoticeType.MESSAGE, "&cPrzeladowano konfiguracje!");

    private Notice playerIsAlreadyWhitelisted = new Notice(NoticeType.MESSAGE, "&cGracz &4[NICK] &cjest juz na whiteliscie!");
    private Notice playerIsAlreadyUnWhitelisted = new Notice(NoticeType.MESSAGE, "&cGracza &4[NICK] &cnie ma na whiteliscie!");

    private Notice playerIsNotWhitelistedBroadcast = new Notice(NoticeType.MESSAGE, "&cGracz &4[NICK] &cpróbował wejść na serwer, ale jest whitelist!");

}
