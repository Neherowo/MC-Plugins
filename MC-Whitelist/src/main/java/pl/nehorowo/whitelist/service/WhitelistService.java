package pl.nehorowo.whitelist.service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class WhitelistService {

    private final Set<UUID> whitelist = new HashSet<>();

    private static WhitelistService instance;

    public static WhitelistService getInstance() {
        if(instance == null) return new WhitelistService();
        return instance;
    }

    public void addPlayer(UUID uuid) {
        if(whitelist.contains(uuid)) {
            System.out.println("Player already whitelisted!");
            return;
        }

        whitelist.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        if(!whitelist.contains(uuid)) {
            System.out.println("Player not whitelisted!");
            return;
        }

        whitelist.remove(uuid);
    }
}
