package pl.nehorowo.tools.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.tools.user.ban.Ban;
import pl.nehorowo.tools.user.bubble.ChatBubble;
import pl.nehorowo.tools.user.check.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter@Setter
public class User {

    @NotNull private UUID uuid;
    private boolean socialSpy;

    private Player lastMsg;

    private int teleportDelay;
    private long lastMessage;

    private List<Player> tpList = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    private Check check = new Check(false, null);
    private ChatBubble chatBubble;


}
