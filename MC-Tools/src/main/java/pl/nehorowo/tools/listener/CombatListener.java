package pl.nehorowo.tools.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.user.combat.Combat;

public record CombatListener(ToolsPlugin plugin) implements Listener {

    public CombatListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player player && event.getDamager() instanceof Player killer)) return;

        if(player.getGameMode() == GameMode.CREATIVE || killer.getGameMode() == GameMode.CREATIVE) return;
        User user = plugin.getUserFactory().findUser(player.getUniqueId());
        if(user == null) return;

        if(user.getCheck().isChecked()) {
            plugin.getMessageConfiguration()
                    .getPlayerIsChecked()
                    .send(player);
            event.setCancelled(true);
            return;
        }

        if(!plugin.getCombatFactory().isInCombat(player)) {
            plugin.getCombatFactory().addCombat(killer, player);

            plugin.getMessageConfiguration()
                    .getYouAreInCombat()
                    .send(player);
        }
        else plugin.getCombatFactory().setCombat(player);

        if(!plugin.getCombatFactory().isInCombat(killer)) {
            plugin.getCombatFactory().addCombat(player, killer);

            plugin.getMessageConfiguration()
                    .getYouAreInCombat()
                    .send(killer);
        }
        else plugin.getCombatFactory().setCombat(killer);
    }
}
