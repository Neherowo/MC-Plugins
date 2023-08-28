package pl.nehorowo.whitelist.service;

import com.google.common.collect.ImmutableMultimap;
import lombok.Getter;
import org.bukkit.entity.Player;
import pl.nehorowo.whitelist.WhitelistPlugin;

import java.util.HashSet;
import java.util.Set;

public class WhitelistService {

    private static WhitelistService instance;

    @Getter private Set<String> whitelistSet = new HashSet<>();
    @Getter public boolean whitelistEnabled = false;

    public static WhitelistService getInstance() {
        if(instance == null) return new WhitelistService();
        return instance;
    }

    public void info() {
        System.out.println("WhitelistService info");
        System.out.println(whitelistSet == null ? "whitelistSet is null" : "whitelistSet is not null");
        System.out.println(whitelistSet.size());
        System.out.println(whitelistSet.toString());
    }

    public int injectWhitelist() {
        whitelistSet.addAll(WhitelistPlugin.getInstance().getWhitelistPeopleConfiguration().getWhitelistPeople());

        return whitelistSet.size();
    }

    public void addPlayer(String player, Player sender) {
        if(whitelistSet.contains(player)) {
            WhitelistPlugin.getInstance().getMessageConfiguration()
                    .getPlayerIsAlreadyWhitelisted()
                    .addPlaceholder(ImmutableMultimap.of("[NICK]", player))
                    .send(sender);
            return;
        }

        whitelistSet.add(player);
    }

    public void removePlayer(String player, Player sender) {
        if(!whitelistSet.contains(player)) {
            WhitelistPlugin.getInstance().getMessageConfiguration()
                    .getPlayerIsAlreadyUnWhitelisted()
                    .addPlaceholder(ImmutableMultimap.of("[NICK]", player))
                    .send(sender);
            return;
        }

        whitelistSet.remove(player);
    }

    public boolean isWhitelisted(String player) {
        return whitelistSet.stream().map(String::toLowerCase).anyMatch(it -> it.equals(player.toLowerCase()));
    }

    public String getWhitelist() {
        StringBuilder builder = new StringBuilder();
        whitelistSet.forEach(it -> builder.append(it).append(", "));
        return builder.toString();
    }
}
