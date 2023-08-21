package pl.nehorowo.treasuremap.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import pl.nehorowo.treasuremap.notice.Notice;
import pl.nehorowo.treasuremap.notice.NoticeType;

@Getter
public class MessageConfiguration extends OkaeriConfig {

    private Notice noPermission = new Notice(NoticeType.MESSAGE, "&cNie masz uprawnien do uzycia tej komendy!");
    private Notice incorrectPlayer = new Notice(NoticeType.MESSAGE, "&cGracz &4[PLAYER] &cnie jest na serwerze!");
    private Notice correctUsage = new Notice(NoticeType.MESSAGE, "&cPoprawne uzycie: &4[USAGE]");
    private Notice reloaded = new Notice(NoticeType.MESSAGE, "&aPrzeladowano konfiguracje!");
    private Notice cannotStartTreasure = new Notice(NoticeType.MESSAGE, "&cNie mozesz rozpoczac poszukiwan skarbu, poniewaz juz masz zaczete!");
}
