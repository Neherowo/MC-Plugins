package pl.nehorowo.tools.task;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.user.combat.Combat;
import pl.nehorowo.tools.utils.TextUtil;
import pl.nehorowo.tools.utils.TimeUtil;

public record CombatTask(ToolsPlugin plugin) implements Runnable {

    @Override
    public void run() {
        plugin.getCombatFactory().getCombat().entrySet().removeIf(entry -> {
            Player player = entry.getKey();
            if(player == null) return true;

            Combat combat = entry.getValue();
            long time = combat.getTime();
            long finalTime = (time - System.currentTimeMillis()) / 1000;

            if(finalTime <= 0) {
                TextUtil.sendActionBar(player, plugin.getMessageConfiguration().getCombatEnd());
                plugin.getCombatFactory().removeCombat(player);
                return true;
            } else {
                String message = plugin.getMessageConfiguration().getCombatTime()
                        .replace("[TIME]", TimeUtil.longToString(finalTime * 100));

                TextUtil.sendActionBar(player, message);
                return false;
            }
        });
    }
}
