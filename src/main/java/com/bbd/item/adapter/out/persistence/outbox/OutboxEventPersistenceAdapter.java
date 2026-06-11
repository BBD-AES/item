package com.bbd.item.adapter.out.persistence.outbox;

import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

}
