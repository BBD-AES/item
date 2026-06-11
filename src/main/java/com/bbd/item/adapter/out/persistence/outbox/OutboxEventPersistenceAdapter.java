package com.bbd.item.adapter.out.persistence.outbox;

import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxEventPersistenceAdapter implements OutboxEventPort {

    private final OutboxEventJpaRepository outboxEventJpaRepository;
    private final OutboxEventPersistenceMapper outboxEventPersistenceMapper;

    // 변경된 상태 저장
    @Override
    public void save(OutboxEvent outBoxEvent) {

        outboxEventJpaRepository.save(outboxEventPersistenceMapper.toEntity(outBoxEvent));
    }


    @Override
    public List<OutboxEvent> getOutboxEvents(OutboxStatus outboxStatus) {
        List<OutboxEventJpaEntity> jpaEvents = outboxEventJpaRepository.findByStatus(outboxStatus);
        return jpaEvents.stream()
                .map(e -> outboxEventPersistenceMapper.toDomain(e))
                .toList();
    }

}
