package moe.pine.izetta.config;

import moe.pine.github.contribution.stats.ContributionStatsClient;
import moe.pine.izetta.github.contributions.GitHubContributions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubContributionsConfig {
    private static final int MAX_ATTEMPTS = 5;

    @Bean
    public ContributionStatsClient contributionStatsClient() {
        return ContributionStatsClient.create();
    }

    @Bean
    public GitHubContributions githubContributions(
        final ContributionStatsClient contributionStatsClient
    ) {
        return new GitHubContributions(contributionStatsClient, MAX_ATTEMPTS);
    }
}
