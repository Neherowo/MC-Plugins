package pl.nehorowo.reward.bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import pl.nehorowo.reward.bot.command.api.Command;

public class RewardCommand extends Command {

    public RewardCommand() {
        super(
                "reward",
                "Komenda sluzaca do wyslania wiadomsci embed!",
                true
        );
    }

    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if(member == null || member.getRoles().stream().noneMatch(role -> role.getId().equals(String.valueOf(getBotConfiguration().getRoleId())))) {
            event.reply(getBotConfiguration().getNoPermission())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        event.reply("Wyslano wiadomosc embed!")
                .setEphemeral(true)
                .queue();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle(getBotConfiguration().getEmbedTitle())
                .setDescription(getBotConfiguration().getEmbedDescription())
                .setFooter(getBotConfiguration().getEmbedFooter())
                .build();

        Button button = Button.primary("mc-rewardbutton", getBotConfiguration().getEmbedButtonLabel());

        MessageChannelUnion channel = event.getChannel();
        channel.sendMessageEmbeds(embed)
                .addActionRow(new ItemComponent[] { button })
                .queue();
    }
}
