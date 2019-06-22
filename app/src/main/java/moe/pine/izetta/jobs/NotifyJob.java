package moe.pine.izetta.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.pine.izetta.services.ContributionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotifyJob {
    private final ContributionService contributionService;

    @ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true")
    @Scheduled(cron = "0 * * * * *")
    @Retryable
    public void notify_1100() {
        final int contribution = contributionService.getContribution();
        log.debug("contribution={}", contribution);
    }
}
