package pl.nehorowo.tools.user.ban;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter@Setter@AllArgsConstructor
public class Ban {

    private Player admin;
    private Player player;
    private long time;
    private String reason;

}
