package com.bbd.item.adapter.out.persistence.outbox;

import com.bbd.item.domain.model.outbox.OutboxEvent;
import org.springframework.stereotype.Component;

@Component
public class OutboxEventPersistenceMapper {

    public OutboxEventJpaEntity toEntity(OutboxEvent outboxEvent) {
        return new OutboxEventJpaEntity(
                outboxEvent.getEventId(),
                outboxEvent.getAggregateType(),
                outboxEvent.getAggregateId(),
                outboxEvent.getEventType(),
                outboxEvent.getPayload(),
                outboxEvent.getStatus(),
                outboxEvent.getRetryCount(),
                outboxEvent.getCreatedAt(),
                outboxEvent.getUpdatedAt(),
                outboxEvent.getPublishedAt(),
                outboxEvent.getVersion()

        );
    }

    public OutboxEvent toDomain(OutboxEventJpaEntity entity) {
        return OutboxEvent.of(
                entity.getEventId(),
                entity.getAggregateType(),
                entity.getAggregateId(),
                entity.getEventType(),
                entity.getPayload(),
                entity.getStatus(),
                entity.getRetryCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getPublishedAt(),
                entity.getVersion()
        );
    }
}