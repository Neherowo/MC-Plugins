package pl.nehorowo.reward.service;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import pl.nehorowo.reward.RewardPlugin;
import pl.nehorowo.reward.bot.command.RewardCommand;
import pl.nehorowo.reward.bot.command.api.CommandManager;
import pl.nehorowo.reward.bot.listener.InteractButtonListener;
import pl.nehorowo.reward.bot.listener.InteractModalListener;
import pl.nehorowo.reward.utils.TextUtil;

@Getter
public class BotService {

    private static BotService instance;
    private JDA jda;

    public static BotService getInstance() {
        if(instance == null) instance = new BotService();
        return instance;
    }

    public void setupBot() {
        try {
            jda = JDABuilder.createDefault(RewardPlugin.getInstance().getBotConfiguration().getBotToken())
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();

            jda.addEventListener(
                    new InteractButtonListener(),
                    new InteractModalListener()
            );

            CommandManager.registerManager(jda);
            new RewardCommand();
            TextUtil.sendLogger("Zalogowano jako " + jda.getSelfUser().getAsTag());
        } catch (Exception e) {
            TextUtil.sendLogger("Wystapil problem podczas logowania bota!");
        }
    }

}
