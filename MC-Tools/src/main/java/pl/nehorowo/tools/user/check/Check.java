package pl.nehorowo.tools.user.check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@AllArgsConstructor@Getter@Setter
public class Check {

    private boolean checked;
    private Location playerLocation;

}
