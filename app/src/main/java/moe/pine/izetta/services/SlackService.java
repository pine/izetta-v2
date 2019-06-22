package moe.pine.izetta.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.pine.izetta.properties.SlackProperties;
import moe.pine.izetta.slack.Message;
import moe.pine.izetta.slack.Slack;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackService {
    private final Slack slack;
    private final SlackProperties slackProperties;

    public void notifyAlert() {
        final Message message =
            Message.builder()
                .token(slackProperties.getToken())
                .channel(slackProperties.getChannel())
                .text(slackProperties.getText())
                .build();

        slack.postMessage(message);
    }
}
