package pl.nehorowo.reward.database.serializer;

import pl.nehorowo.reward.controller.UserController;
import pl.nehorowo.reward.database.api.ItemSerializer;
import pl.nehorowo.reward.service.UserService;

import java.util.UUID;

public class UserControllerSerializer implements ItemSerializer<UserController> {
    @Override
    public Class<UserController> supportedClass() {
        return UserController.class;
    }

    @Override
    public UserController serialize(String s) {
        return UserService.getInstance().compute(UUID.fromString(s)).join();
    }

    @Override
    public String deserialize(UserController userController) {
        return userController.getUuid().toString();
    }
}
