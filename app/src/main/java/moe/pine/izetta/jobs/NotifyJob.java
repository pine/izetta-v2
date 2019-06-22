package moe.pine.izetta.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moe.pine.izetta.services.NotifyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotifyJob {
    private final NotifyService notifyService;

    @ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true")
    @Scheduled(cron = "0 11 * * * *")
    @Retryable
    public void notify_1100() {
        notifyService.run();
    }
}
