package moe.pine.izetta.config;

import moe.pine.izetta.github.contributions.GitHubContributions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;

@Configuration
public class GitHubContributionsConfig {
    @Bean
    public GitHubContributions githubContributions(
        @Nonnull final RestTemplate restTemplate
    ) {
        return new GitHubContributions(restTemplate);
    }
}
