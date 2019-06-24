package moe.pine.izetta.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.pine.izetta.github.contributions.GitHubContributions;
import moe.pine.izetta.properties.GitHubProperties;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

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

        final Optional<Integer> contributionOpt =
            Optional.ofNullable(contributions.get(dt));

        log.debug(
            "Contribution collected :: username={}, dt={}, contribution={}",
            username, dt, contributionOpt);

        final int contribution = contributionOpt.orElse(0);
        if (contribution < 0) {
            throw new RuntimeException(
                String.format(
                    "Illegal contribution value :: contribution=%s",
                    contribution));
        }

        return contribution;
    }
}
