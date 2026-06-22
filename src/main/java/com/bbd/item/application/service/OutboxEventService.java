package com.bbd.item.application.service;

import com.bbd.item.adapter.out.persistence.outbox.OutboxEventProcessor;
import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxEventService {

    private static final int BATCH_SIZE = 100;

    private final OutboxEventPort outboxEventPort;
    private final OutboxEventProcessor outboxEventProcessor;

    public void publishPendingEvent() {
        // 1. Event 상태가 PENDING 인 이벤트 리스트로 조회
        List<OutboxEvent> outboxEvents = outboxEventPort.getPendingOutboxEvents(BATCH_SIZE);

        // 바꿀 것이 없다면 아래 로직 실행 x
        if(outboxEvents.isEmpty()){
            return;
        }

        // 2. Outbox Pattern Processor 활용
        for(OutboxEvent outboxEvent : outboxEvents) {
            outboxEventProcessor.process(outboxEvent);
        }

    }


}
