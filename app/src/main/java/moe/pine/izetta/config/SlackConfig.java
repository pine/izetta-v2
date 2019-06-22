package moe.pine.izetta.config;

import moe.pine.izetta.slack.Slack;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfig {
    @Bean
    public Slack slack(final RestTemplateBuilder restTemplateBuilder) {
        return new Slack(restTemplateBuilder);
    }
}
