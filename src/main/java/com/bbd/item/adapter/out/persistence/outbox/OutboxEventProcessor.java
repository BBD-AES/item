package com.bbd.item.adapter.out.persistence.outbox;

import com.bbd.item.application.port.out.ItemEventPublisher;
import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventProcessor {

    private static final int MAX_RETRY_COUNT = 10;
    private final OutboxEventPort outboxEventPort;
    private final ItemEventPublisher itemEventPublisher;

    @Transactional
    public void process(OutboxEvent outboxEvent) {
        // 2. outboxEvents 리스트 돌면서 성공 or 실패 처리
        try {
            // 1. Kafka이벤트 발행 시도
            itemEventPublisher.publish(outboxEvent);

            // 2. DB 이벤트 상태 변경
            outboxEvent.markSent();

            // 3. 업데이트 상태 저장
            outboxEventPort.save(outboxEvent);

            log.info("Outbox event sent successfully : {}" , outboxEvent.getEventId());

        } catch (Exception e) {
            // 1. DB 시도 회수 증가
            outboxEvent.increaseRetryCount();

            // 2. 회수 상태 10번 이상인 경우 실패 처리
            if (outboxEvent.getRetryCount() > MAX_RETRY_COUNT) {
                outboxEvent.markFailed();
            }

            // 2. 업데이트 상태 저장
            outboxEventPort.save(outboxEvent);
        }
    }


}
