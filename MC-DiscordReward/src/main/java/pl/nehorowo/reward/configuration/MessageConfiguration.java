package pl.nehorowo.reward.configuration;


import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import pl.nehorowo.reward.notice.Notice;
import pl.nehorowo.reward.notice.NoticeType;

@Getter
public class MessageConfiguration extends OkaeriConfig {

    private Notice youClaimedReward = new Notice(NoticeType.SUBTITLE, "&aOdebrales nagrode za dolaczenie na discord!");
}
