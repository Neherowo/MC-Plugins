package pl.nehorowo.reward.bot.listener;

import lombok.NonNull;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class InteractButtonListener extends ListenerAdapter {


    public void onButtonInteraction(ButtonInteractionEvent event) {
        Button button = event.getButton();
        String id = button.getId();
        if(id == null) return;
        if(!id.equals("mc-rewardbutton")) return;

        TextInput text = TextInput.create("minecraft-nick", "Podaj swoj nick na serwerze Minecraft", TextInputStyle.SHORT)
                .setRequired(true)
                .build();

        Modal modal = Modal.create("mc-reward", "Weryfikacja nicku")
                .addActionRows(new ActionRow[] { ActionRow.of(text) })
                .build();

        event.replyModal(modal).queue();
    }
}
