package moe.pine.izetta.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.pine.izetta.github.contributions.GitHubContributions;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotifyService {
    private final Clock clock;
    private final GitHubContributions githubContributions;

    public void run() {
        final String username = "";
        final LocalDate dt = LocalDate.now(clock);
        final var contributions = githubContributions.collect(username);

        if (!contributions.containsKey(dt)) {
            throw new RuntimeException(
                String.format(
                    "The contribution is not found :: dt=%s, username=%s, contributions=%s",
                    dt, username, contributions));
        }

        final int contribution = contributions.get(dt);
        if (contribution > 0) return; // Already committed


    }
}
