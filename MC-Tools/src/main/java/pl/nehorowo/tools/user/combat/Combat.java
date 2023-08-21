package pl.nehorowo.tools.user.combat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor@Getter@Setter
public class Combat {


    private final UUID uuid;
    private long time;
}
