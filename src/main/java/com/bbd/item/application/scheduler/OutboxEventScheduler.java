package com.bbd.item.application.scheduler;


import com.bbd.item.application.service.OutboxEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventScheduler {

    private final OutboxEventService outboxEventService;

    @Scheduled(fixedRate = 5000) // 5초에 1번씩
    public void eventScheduler() {
        outboxEventService.publishPendingEvent();
    }


}
