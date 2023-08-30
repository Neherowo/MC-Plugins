package pl.nehorowo.reward.database.serializer;

import pl.nehorowo.reward.database.api.ItemSerializer;

import java.util.UUID;

public class UUIDSerializer implements ItemSerializer<UUID> {
    @Override
    public Class<UUID> supportedClass() {
        return UUID.class;
    }

    @Override
    public UUID serialize(String s) {
        return UUID.fromString(s);
    }

    @Override
    public String deserialize(UUID uuid) {
        return uuid.toString();
    }
}
