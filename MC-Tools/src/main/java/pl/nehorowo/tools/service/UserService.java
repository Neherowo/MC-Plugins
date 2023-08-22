package pl.nehorowo.tools.service;

import lombok.Getter;
import pl.nehorowo.tools.database.api.DatabaseCache;
import pl.nehorowo.tools.controller.UserController;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserService implements DatabaseCache<UserController> {

    private static UserService instance;

    public static UserService getInstance() {
        if(instance == null) instance = new UserService();
        return instance;
    }

    @Getter private final Map<UUID, UserController> userControllerMap = new HashMap<>();

    public CompletableFuture<UserController> compute(UUID uuid){
        return CompletableFuture.completedFuture(userControllerMap.computeIfAbsent(uuid, UserController::new));
    }

    public Optional<UserController> get(UUID uuid){
        return Optional.ofNullable(userControllerMap.get(uuid));
    }

    @Override
    public void add(UserController userController) {
        userControllerMap.put(userController.getUuid(), userController);
    }
}
