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

    @Retryable
    @Scheduled(cron = "0  0 22 * * *")
    @Scheduled(cron = "0  0 23 * * *")
    @Scheduled(cron = "0 30 23 * * *")
    @ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true")
    public void run() {
        final int contribution = contributionService.getContribution();
        if (contribution == 0) {
            slackService.notifyAlert();
        }
    }
}
