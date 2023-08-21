package pl.nehorowo.vanish.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import pl.nehorowo.vanish.notice.Notice;
import pl.nehorowo.vanish.notice.NoticeType;

@Getter
public class MessageConfiguration extends OkaeriConfig {

    private Notice noPermission = new Notice(NoticeType.MESSAGE, "&cNie posiadasz uprawnien do tej komendy!");
    private Notice correctUsage = new Notice(NoticeType.MESSAGE, "&8** &fPoprawne uzycie: &4[USAGE]");
    private Notice incorrectPlayer = new Notice(NoticeType.MESSAGE, "&cGracz o podanej nazwie nie istnieje!");

    private Notice turnedOffVanish = new Notice(NoticeType.SUBTITLE, "&cWylaczyles tryb niewidzialnosci!");
    private Notice turnedOnVanish = new Notice(NoticeType.SUBTITLE, "&aWlaczyles tryb niewidzialnosci!");

    private Notice vanishList = new Notice(NoticeType.MESSAGE, "&aLista graczy w trybie niewidzialnosci:");
    private String vanishListFormat = "&8-> &2[PLAYER]";

    private Notice reloaded = new Notice(NoticeType.MESSAGE, "&aPrzeladowano konfiguracje!");
}
