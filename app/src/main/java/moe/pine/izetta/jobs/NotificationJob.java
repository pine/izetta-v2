package moe.pine.izetta.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.pine.izetta.services.ContributionService;
import moe.pine.izetta.services.SlackService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationJob {
    private final ContributionService contributionService;
    private final SlackService slackService;

    @ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true")
    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "30 * * * * *")
    @Retryable
    public void run() {
        final int contribution = contributionService.getContribution();
        log.debug("contribution={}", contribution);

        if (contribution == 0) {
            slackService.notifyAlert();
        }
    }
}
