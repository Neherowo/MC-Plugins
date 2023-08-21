package pl.nehorowo.tools.factory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.user.combat.Combat;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CombatFactory {

    @Getter
    private ConcurrentMap<Player, Combat> combat = new ConcurrentHashMap<>();

    public Combat findCombat(Player player) {
        return combat.get(player);
    }

    public void addCombat(Player attacker, Player victim) {
        combat.put(attacker, new Combat(
                attacker.getUniqueId(),
                System.currentTimeMillis() + (1000L * ToolsPlugin.getInstance().getConfiguration().getCombatTime()) + 1000L)
        );

        combat.put(victim, new Combat(
                attacker.getUniqueId(),
                System.currentTimeMillis() + (1000L * ToolsPlugin.getInstance().getConfiguration().getCombatTime()) + 1000L)
        );
    }

    public void setCombat(Player player) {
        Combat combat = findCombat(player);
        combat.setTime(System.currentTimeMillis() + (1000L * ToolsPlugin.getInstance().getConfiguration().getCombatTime()) + 1000L);
    }

    public void removeCombat(Player player) {
        combat.remove(player);
    }

    public boolean isInCombat(Player player) {
        return combat.containsKey(player);
    }
}
