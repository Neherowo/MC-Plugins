package pl.nehorowo.tools.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.service.UserService;

public record CombatListener(ToolsPlugin plugin) implements Listener {

    public CombatListener(ToolsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player player && event.getDamager() instanceof Player killer)) return;

        if(player.getGameMode() == GameMode.CREATIVE || killer.getGameMode() == GameMode.CREATIVE) return;
        UserService.getInstance().get(player.getUniqueId()).ifPresent(user -> {
            if(user.getCheck().isChecked()) {
                plugin.getMessageConfiguration()
                        .getPlayerIsChecked()
                        .send(player);
                event.setCancelled(true);
                return;
            }

            if(!plugin.getCombatController().isInCombat(player)) {
                plugin.getCombatController().addCombat(killer, player);

                plugin.getMessageConfiguration()
                        .getYouAreInCombat()
                        .send(player);
            }
            else plugin.getCombatController().setCombat(player);

            if(!plugin.getCombatController().isInCombat(killer)) {
                plugin.getCombatController().addCombat(player, killer);

                plugin.getMessageConfiguration()
                        .getYouAreInCombat()
                        .send(killer);
            }
            else plugin.getCombatController().setCombat(killer);
        });
    }
}
