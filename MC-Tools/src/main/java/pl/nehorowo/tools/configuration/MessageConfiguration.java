package pl.nehorowo.tools.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import pl.nehorowo.tools.notice.Notice;
import pl.nehorowo.tools.notice.NoticeType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter

@Header("Plik konfiguracyjny messages.yml")
@Header("")
@Header(" Wszystkie wiadomości w pluginie są konfigurowalne.")
@Header(" Plugin wspiera HEX kolory. Ich format to: &#0000ff (gdzie 0000ff to kolor w HEX).")
@Header(" Dziękuje za uzywanie pluginu.")
public class MessageConfiguration extends OkaeriConfig {

    @Comment("Dostępne NoticeType: TITLE, SUBTITLE, MESSAGE, ACTIONBAR")
    private Notice correctUsage = new Notice(NoticeType.MESSAGE, "&cPoprawne uzycie: &4[USAGE]");
    private Notice incorrectPlayer = new Notice(NoticeType.MESSAGE, "&cNie znaleziono gracza o nicku &4[PLAYER]");
    private Notice noPermission = new Notice(NoticeType.MESSAGE, "&cNie posiadasz uprawnien do tej komendy!");

    private Notice msgSenderFormat = new Notice(NoticeType.MESSAGE, "&eJa &7-> &6[PLAYER] &8-> &f[MESSAGE]");
    private Notice msgTargetFormat = new Notice(NoticeType.MESSAGE, "&6[PLAYER] &7-> &eJa &8-> &f[MESSAGE]");

    private Notice msgSpyFormat = new Notice(NoticeType.MESSAGE, "&e&lSOCIALSPY &8** &6[PLAYER] &8-> &6[TARGET] &8-> &f[MESSAGE]");
    private Notice msgSpyToggle = new Notice(NoticeType.SUBTITLE, "&fSocialSpy zostal [STATUS]!");

    private Notice noLastMsg = new Notice(NoticeType.MESSAGE, "&cNie pisałeś z żadnym graczem (bądź jest offline)!");
    private Notice teleportedToPlayer = new Notice(NoticeType.SUBTITLE, "&fPrzeteleportowano do &e[PLAYER]");
    private Notice teleportedToLocation = new Notice(NoticeType.SUBTITLE, "&ePrzeteleportowano na podane kordynaty.");
    private Notice cantMsgToYourself = new Notice(NoticeType.SUBTITLE, "&cNie możesz napisać sam do siebie!");

    private Notice cantTeleportToYourself = new Notice(NoticeType.SUBTITLE, "&cNie możesz się przeteleportować do siebie!");
    private Notice noTpRequests = new Notice(NoticeType.SUBTITLE, "&cNie posiadasz żadnych prośb o teleportacje!");
    private Notice noTpRequestsPlayer = new Notice(NoticeType.SUBTITLE, "&cGracz &4[PLAYER] &cnie prosi cię o teleportację!");
    private Notice alreadyTeleported = new Notice(NoticeType.SUBTITLE, "&cJuż wysłałeś prośbę o teleportacje do tego gracza!");
    private Notice tpaPlayerRequest = new Notice(NoticeType.MESSAGE, "&aWysłano prośbę o teleportacja do gracza [PLAYER]");
    private Notice tpaTargetRequest = new Notice(NoticeType.MESSAGE, " &8-> &fGracz &e[PLAYER] &fchce sie do Ciebie przeteleportowac. \n &8-> &fAby zaakceptowac wpisz &6/tpaccept [PLAYER]");
    private Notice acceptedRequest = new Notice(NoticeType.SUBTITLE, "&aZaakceptowano prośbę o teleportacje!");
    private Notice teleportCancelled = new Notice(NoticeType.SUBTITLE, "&cTeleportacja zostala anulowana!");
    private Notice timeToTeleport = new Notice(NoticeType.SUBTITLE, "&aZa [TIME] sekund zostaniesz przeteleportowany!");
    private Notice teleported = new Notice(NoticeType.SUBTITLE, "&aZostałeś przeteleportowany!");

    private Notice incorrectGameMode = new Notice(NoticeType.MESSAGE, "&cNie znaleziono trybu gry o nazwie &4[GAMEMODE]");
    private Notice changedGamemode = new Notice(NoticeType.SUBTITLE, "&aZmieniono tryb gry na [GAMEMODE]");
    private Notice changedTargetGamemode = new Notice(NoticeType.MESSAGE, "&aZmieniono tryb gry gracza [PLAYER] na [GAMEMODE]");

    private Notice setSpawn = new Notice(NoticeType.SUBTITLE, "&aUstawiono spawn serwera!");

    private Notice youAreInCombat = new Notice(NoticeType.MESSAGE, "&cJesteś w walce! Nie możesz się wylogować teraz przez 20 sekund!");
    private Notice playerLoggedWhileCombat = new Notice(NoticeType.MESSAGE, "&6&lBUUUU! &fGracz &e[PLAYER] &fwylogowal sie podczas walki!");
    private String combatTime = "&6&lANTYLOGOUT &8** &fPozostało Ci &e[TIME] &fsekund do wyjścia z walki!";
    private String combatEnd = "&aZakończyłeś walkę! Możesz się już wylogować!";

    private Notice chatIsAlreadyMuted = new Notice(NoticeType.SUBTITLE, "&cChat jest już wyciszony!");
    private Notice chatIsAlreadyUnMuted = new Notice(NoticeType.SUBTITLE, "&aChat jest już włączony!");
    private Notice mutedChat = new Notice(NoticeType.SUBTITLE, "&cWyłączono chat!");
    private List<String> mutedChatBroadcast = Arrays.asList(" ", "&c&lCHAT &8** &fChat zostal wyciszony przez &e[PLAYER]", " ");
    private Notice unMutedChat = new Notice(NoticeType.SUBTITLE, "&aWłączono chat!");
    private List<String> unMutedChatBroadcast = Arrays.asList(" ", "&c&lCHAT &8** &fChat zostal wyciszony przez &e[PLAYER]", " ");
    private Notice clearChat = new Notice(NoticeType.SUBTITLE, "&aWyczyszczono chat!");
    private List<String> clearChatBroadcast = Arrays.asList(" ", "&c&lCHAT &8** &fChat zostal wyczyszczony przez &e[PLAYER]", " ");
    private Notice chatIsMuted = new Notice(NoticeType.MESSAGE, "&cChat jest wyłączony!");
    private Notice playerTriedToMute = new Notice(NoticeType.MESSAGE, "&c&lCHAT &8** &fGracz &c[PLAYER] &fpróbował napisać wiadomość, ale chat jest &4wyłączony!");

    private Notice incorrectItem = new Notice(NoticeType.MESSAGE, "&cPodany przedmiot nie istnieje.");
    private Notice gaveItem = new Notice(NoticeType.SUBTITLE, "&aDano ci &7[AMOUNT]x &a[ITEM]");
    private Notice gaveItemTarget = new Notice(NoticeType.SUBTITLE, "&aDano graczowi &e[PLAYER] &7[AMOUNT]x &a[ITEM]");

    private Notice youHaveCooldown = new Notice(NoticeType.MESSAGE, "&cNa czacie można pisać co &43 sekundy!");

    private Notice feed = new Notice(NoticeType.SUBTITLE, "&aZostales nakarmiony!");
    private Notice feededPlayer = new Notice(NoticeType.SUBTITLE, "&fNakarmiles [PLAYER]!");
    private Notice feededTarget = new Notice(NoticeType.SUBTITLE, "&aZostales nakarmiony!");

    private Notice heal = new Notice(NoticeType.SUBTITLE, "&aZostales uleczony!");
    private Notice healedPlayer = new Notice(NoticeType.SUBTITLE, "&fUleczyles [PLAYER]!");
    private Notice healedTarget = new Notice(NoticeType.SUBTITLE, "&aZostales uleczony!");

    private Notice listPlayersSize = new Notice(NoticeType.SUBTITLE, "&fIlość graczy online: &e[PLAYERS]");
    private Notice listPlayers = new Notice(NoticeType.SUBTITLE, "&fGracze online: &e[PLAYERS]");

    private Notice messageHistoryIsEmpty = new Notice(NoticeType.MESSAGE, "&cHistoria wiadomości gracza jest pusta!");

    private Notice alreadyChecked = new Notice(NoticeType.MESSAGE, "&cGracz zostal juz sprawdzony!");
    private Notice checkedPlayer = new Notice(NoticeType.SUBTITLE, "&aZaczales sprawdzac gracza [PLAYER]!");
    private Notice checkedTarget = new Notice(NoticeType.SUBTITLE, "&cJesteś sprawdzany [PLAYER]");

    private Notice notChecked = new Notice(NoticeType.MESSAGE, "&cGracz nie jest sprawdzany!");
    private String checkReminderTitle = "&c&lSPRAWDZANIE";
    private String checkReminderSubTitle = "&cJesteś sprawdzany! Nie wychodź z serwera, wykonuj polecenia administratora!";
    private List<String> checkRemiderMessage = Arrays.asList(" ", "&c&lSPRAWDZANIE &8** &fJesteś sprawdzany! Nie wychodź z serwera, wykonuj polecenia administratora!", " ");

    private String kickedPlayerBroadcast = "&cGracz &4[PLAYER] &czostał wyrzucony z serwera! &cPowód: &4[REASON]";
    private String bannedPlayerBroadcast = "&cGracz &4[PLAYER] &czostał zbanowany! &cPowód: &4[REASON] na [TIME]";
    private Notice incorrectTime = new Notice(NoticeType.MESSAGE, "&cNiepoprawny format czasu! Poprawny to np: 1d1m1h");

    private Notice playerIsChecked = new Notice(NoticeType.MESSAGE, "&cNie mozesz go uderzyc poniewaz ten gracz jest sprawdzany!");
}
