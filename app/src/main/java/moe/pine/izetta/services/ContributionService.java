package moe.pine.izetta.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.pine.izetta.github.contributions.GitHubContributions;
import moe.pine.izetta.properties.GitHubProperties;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContributionService {
    private final Clock clock;
    private final GitHubProperties gitHubProperties;
    private final GitHubContributions githubContributions;

    public int getContribution() {
        final String username = gitHubProperties.getUsername();
        final LocalDate dt = LocalDate.now(clock);
        final var contributions = githubContributions.collect(username);

        if (!contributions.containsKey(dt)) {
            throw new RuntimeException(
                String.format(
                    "The contribution is not found :: dt=%s, username=%s, contributions=%s",
                    dt, username, contributions));
        }

        return contributions.get(dt);
    }
}
