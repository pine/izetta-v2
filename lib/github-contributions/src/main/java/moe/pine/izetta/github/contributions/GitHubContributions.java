package moe.pine.izetta.github.contributions;

import moe.pine.github.contribution.stats.Contribution;
import moe.pine.github.contribution.stats.ContributionStats;
import moe.pine.github.contribution.stats.ContributionStatsClient;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GitHubContributions {
    private final ContributionStatsClient contributionStatsClient;
    private final RetryTemplate retryTemplate;

    public GitHubContributions(
        final ContributionStatsClient contributionStatsClient,
        final int maxAttempts
    ) {
        this.contributionStatsClient = Objects.requireNonNull(contributionStatsClient);
        this.retryTemplate = new RetryTemplate() {{
            final Map<Class<? extends Throwable>, Boolean> retryableExceptions =
                Collections.singletonMap(IOException.class, true);
            final RetryPolicy retryPolicy =
                new SimpleRetryPolicy(maxAttempts, retryableExceptions);

            setRetryPolicy(retryPolicy);
        }};
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Map<LocalDate, Integer> collect(
        final String username
    ) {
        Objects.requireNonNull(username);

        final ContributionStats stats;
        try {
            stats = retryTemplate.execute(args ->
                contributionStatsClient.collect(username));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return stats.getContributions()
            .stream()
            .collect(Collectors.toUnmodifiableMap(
                Contribution::getDate,
                Contribution::getCount));
    }
}
