package pl.nehorowo.reward.service;

import pl.nehorowo.reward.controller.UserController;

import java.util.HashSet;
import java.util.Set;

public class UpdateService {

    private static UpdateService instance;

    public static UpdateService getInstance() {
        if(instance == null) instance = new UpdateService();
        return instance;
    }

    private Set<UserController> userControllerSet = new HashSet<>();

    public Set<UserController> getUserControllerSet() {
        return userControllerSet;
    }
}
