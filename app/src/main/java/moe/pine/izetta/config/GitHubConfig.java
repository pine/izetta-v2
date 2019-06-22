package moe.pine.izetta.config;

import moe.pine.izetta.properties.GitHubProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GitHubProperties.class)
public class GitHubConfig {
}
