package pl.nehorowo.tools.factory;

import pl.nehorowo.tools.user.ban.Ban;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BanFactory {

    private ConcurrentMap<UUID, Ban> bans = new ConcurrentHashMap<>();

    public Ban findBan(UUID uuid) {
        return bans.values().stream().filter(user ->
                user.getPlayer().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public void addBan(Ban ban) {
        bans.putIfAbsent(ban.getPlayer().getUniqueId(), ban);
    }

}
