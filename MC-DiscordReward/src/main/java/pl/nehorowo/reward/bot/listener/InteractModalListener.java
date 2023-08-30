package pl.nehorowo.reward.bot.listener;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nehorowo.reward.RewardPlugin;
import pl.nehorowo.reward.controller.UserController;
import pl.nehorowo.reward.service.UserService;

public class InteractModalListener extends ListenerAdapter {


    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String modalId = event.getModalId();
        if(!modalId.equals("mc-reward")) return;
        String playerName = event.getValue("minecraft-nick").getAsString();
        Player player = Bukkit.getPlayer(playerName);
        if(player == null) {
            event.reply(RewardPlugin.getInstance().getBotConfiguration().getPlayerOffline())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        UserController user = UserService.getInstance().get(player.getUniqueId()).orElse(null);
        if(user != null) {
            if(user.isClaimedReward()) {
                event.reply(RewardPlugin.getInstance().getBotConfiguration().getAlreadyClaimed())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            //idk
            return;
        }

        UserService.getInstance().compute(player.getUniqueId()).thenAccept(userController -> {
            userController.setPlayer(player);
            userController.setName(player.getName());
            userController.setDiscordId(event.getUser().getIdLong());
            userController.setClaimedReward(true);

            userController.update();
        });


        Bukkit.getScheduler().runTask(RewardPlugin.getInstance(), () -> {
            RewardPlugin.getInstance().getConfiguration().getRewardCommands().forEach(command ->
                    Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            command.replace("[PLAYER]", playerName))
            );
        });

        RewardPlugin.getInstance().getMessageConfiguration()
            .getYouClaimedReward().send(player);
    }
}
