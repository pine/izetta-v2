package moe.pine.izetta.github.contributions;

import lombok.SneakyThrows;
import moe.pine.github.contribution.stats.Contribution;
import moe.pine.github.contribution.stats.ContributionStats;
import moe.pine.github.contribution.stats.ContributionStatsClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GitHubContributionsTest {
    private static final int MAX_ATTEMPTS = 3;

    @Mock
    private ContributionStatsClient contributionStatsClient;

    private GitHubContributions gitHubContributions;

    @Before
    public void setUp() {
        gitHubContributions =
            new GitHubContributions(contributionStatsClient, MAX_ATTEMPTS);
    }

    @Test
    @SneakyThrows
    public void collectTest() {
        final List<Contribution> contributions =
            List.of(
                new Contribution(LocalDate.of(2019, 6, 1), 1),
                new Contribution(LocalDate.of(2019, 6, 2), 0),
                new Contribution(LocalDate.of(2019, 6, 3), 3)
            );

        final ContributionStats contributionStats = mock(ContributionStats.class);
        when(contributionStats.getContributions()).thenReturn(contributions);

        when(contributionStatsClient.collect("username"))
            .thenReturn(contributionStats);

        final Map<LocalDate, Integer> expected =
            Map.of(
                LocalDate.of(2019, 6, 1), 1,
                LocalDate.of(2019, 6, 2), 0,
                LocalDate.of(2019, 6, 3), 3
            );
        final Map<LocalDate, Integer> actual =
            gitHubContributions.collect("username");

        assertEquals(expected, actual);

        verify(contributionStats).getContributions();
        verify(contributionStatsClient).collect("username");
    }

    @Test
    @SneakyThrows
    public void collectTest_retryTwice() {
        final List<Contribution> contributions =
            List.of(
                new Contribution(LocalDate.of(2019, 6, 1), 1),
                new Contribution(LocalDate.of(2019, 6, 2), 0),
                new Contribution(LocalDate.of(2019, 6, 3), 3)
            );

        final ContributionStats contributionStats = mock(ContributionStats.class);
        when(contributionStats.getContributions()).thenReturn(contributions);

        when(contributionStatsClient.collect("username"))
            .thenThrow(IOException.class)
            .thenThrow(IOException.class)
            .thenReturn(contributionStats);

        final Map<LocalDate, Integer> expected =
            Map.of(
                LocalDate.of(2019, 6, 1), 1,
                LocalDate.of(2019, 6, 2), 0,
                LocalDate.of(2019, 6, 3), 3
            );
        final Map<LocalDate, Integer> actual =
            gitHubContributions.collect("username");

        assertEquals(expected, actual);

        verify(contributionStats).getContributions();
        verify(contributionStatsClient, times(3)).collect("username");
    }

    @Test(expected = UncheckedIOException.class)
    @SneakyThrows
    public void collectTest_retryForever() {
        final ContributionStats contributionStats = mock(ContributionStats.class);

        when(contributionStatsClient.collect("username"))
            .thenThrow(IOException.class)
            .thenThrow(IOException.class)
            .thenThrow(IOException.class)
            .thenReturn(contributionStats);

        gitHubContributions.collect("username");
    }
}
