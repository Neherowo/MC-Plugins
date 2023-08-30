package pl.nehorowo.reward.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

@Getter
public class BotConfiguration extends OkaeriConfig {

    private String botToken = "token";
    private String roleId = new String("1112354708414267464");
    private String noPermission = "Nie masz uprawnień do użycia tej komendy!";


    private String embedTitle = "Nagroda";
    private String embedDescription = "Kliknij tutaj, aby odebrac nagrode discord!";
    private String embedFooter = "Nagroda Discord";
    private String embedColor = "#00ff00";

    private String embedButtonLabel = "Odbierz nagrode";
    private String embedButtonEmojiID = ":trophy:";

    private String playerOffline = "Aby odebrac nagrode musisz byc online na serwerze!";
    private String alreadyClaimed = "Nagroda zostala juz odebrana!";

}
