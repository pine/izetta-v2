package moe.pine.izetta.config;

import moe.pine.izetta.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
