package pl.nehorowo.tools.factory;

import lombok.Getter;
import pl.nehorowo.tools.user.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UserFactory {

    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();


    public void createUser(UUID uuid) {
        users.computeIfAbsent(uuid, users -> new User(uuid));
    }


    public User findUser(UUID uuid) {
        return users.values().stream().filter(user ->
                user.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public void removeUser(UUID uuid) {
        users.remove(uuid);
    }






}
