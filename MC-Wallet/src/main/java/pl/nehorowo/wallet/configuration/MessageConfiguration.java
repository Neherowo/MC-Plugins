package pl.nehorowo.wallet.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import pl.nehorowo.wallet.notice.Notice;
import pl.nehorowo.wallet.notice.NoticeType;

@Getter

@Header("Plik konfiguracyjny messages.yml")
@Header("")
@Header(" Wszystkie wiadomości w pluginie są konfigurowalne.")
@Header(" Plugin wspiera HEX kolory. Ich format to: &#0000ff (gdzie 0000ff to kolor w HEX).")
@Header(" Dziękuje za uzywanie pluginu.")
public class MessageConfiguration extends OkaeriConfig {

    private Notice noPermission = new Notice(NoticeType.MESSAGE, "&cNie posiadasz uprawnień do tej komendy.");
    private Notice correctUsage = new Notice(NoticeType.MESSAGE, "&cPoprawne użycie: &7[USAGE]");
    private Notice incorrectPlayer = new Notice(NoticeType.MESSAGE, "&cNie znaleziono gracza o podanej nazwie.");

    private Notice wrongAmount = new Notice(NoticeType.MESSAGE, "&cPodana kwota jest nieprawidłowa.");
    private Notice notEnoughtMoney = new Notice(NoticeType.MESSAGE, "&cNie posiadasz wystarczającej ilości pieniędzy na koncie.");
    private Notice addedMoney = new Notice(NoticeType.MESSAGE, "&fDodano &7[AMOUNT] &fdo twojego konta.");
    private Notice addedMoneyAdmin = new Notice(NoticeType.MESSAGE, "&fDodano &7[AMOUNT] &fdo konta gracza &7[PLAYER].");
    private Notice removeMoney = new Notice(NoticeType.MESSAGE, "&aDodano &7[AMOUNT] &ado twojego konta.");
    private Notice removeMoneyAdmin = new Notice(NoticeType.MESSAGE, "&fUsunieto &7[AMOUNT] &fz konta gracza &7[PLAYER]");
    private Notice setMoney = new Notice(NoticeType.MESSAGE, "&fUstawiono twoje pieniądze na &7[AMOUNT].");
    private Notice setMoneyAdmin = new Notice(NoticeType.MESSAGE, "&fUstawiono pieniadze gracza &7[PLAYER]&f na &7[AMOUNT].");

}
