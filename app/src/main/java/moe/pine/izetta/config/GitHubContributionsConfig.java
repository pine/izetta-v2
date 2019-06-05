package moe.pine.izetta.config;

import moe.pine.github.contribution.stats.ContributionStatsClient;
import moe.pine.izetta.github.contributions.GitHubContributions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

@Configuration
public class GitHubContributionsConfig {
    private static final int MAX_ATTEMPTS = 5;

    @Bean
    public ContributionStatsClient contributionStatsClient() {
        return new ContributionStatsClient();
    }

    @Bean
    public GitHubContributions githubContributions(
        @Nonnull final ContributionStatsClient contributionStatsClient
    ) {
        return new GitHubContributions(contributionStatsClient, MAX_ATTEMPTS);
    }
}
