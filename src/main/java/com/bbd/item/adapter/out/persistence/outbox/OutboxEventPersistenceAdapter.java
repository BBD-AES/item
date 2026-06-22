package com.bbd.item.adapter.out.persistence.outbox;

import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    // PENDING 이벤트 배치사이즈 만큼 조회
    @Override
    public List<OutboxEvent> getPendingOutboxEvents(int batchSize) {
        return outboxEventJpaRepository
                .findByStatusOrderByCreatedAtAsc(
                        OutboxStatus.PENDING,
                        PageRequest.of(0, batchSize)
                )
                .stream()
                .map( e -> outboxEventPersistenceMapper.toDomain(e))
                .toList();
    }

}
