package pl.nehorowo.tools.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter@Setter@AllArgsConstructor
public class Teleport {

    private Location from;
    private Location to;

}