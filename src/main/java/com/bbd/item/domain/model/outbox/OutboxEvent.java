package com.bbd.item.domain.model.outbox;

import com.bbd.item.adapter.out.kafka.ItemKafkaRequest;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class OutboxEvent {

    private final String eventId;
    private final String aggregateType;
    private final String aggregateId;
    private final OutboxEventType eventType;
    private final String payload;

    private OutboxStatus status;
    private int retryCount;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;

    private OutboxEvent(
            String eventId,                 // UUID 랜덤
            String aggregateType,           // 발행자 타입
            String aggregateId,             // sku
            OutboxEventType eventType,      // 이벤트 타입
            String payload,                 // payload (발행 할 메시지)
            OutboxStatus status,            // 아웃박스 상태 (PENDING, SENT, FAILED)
            int retryCount,                 // 시도 횟수
            LocalDateTime createdAt,        // 만든 시간
            LocalDateTime updatedAt,        // 업데이트 시간
            LocalDateTime publishedAt       // 발행 시간
    ) {
        this.eventId = eventId;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
        this.retryCount = retryCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.publishedAt = publishedAt;
    }

    public static OutboxEvent itemPriceChanged(
            String eventId,
            String sku,
            String payload
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new OutboxEvent(
                eventId,
                "item",
                sku,
                OutboxEventType.ITEM_PRICE_CHANGED,
                payload,
                OutboxStatus.PENDING,
                0,
                now,
                now,
                null
        );
    }

    // Entity -> domain 변환 시
    public static OutboxEvent of(
            String eventId,
            String aggregateType,
            String aggregateId,
            OutboxEventType eventType,
            String payload,
            OutboxStatus status,
            int retryCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime publishedAt
    ) {
        return new OutboxEvent(
                eventId,
                aggregateType,
                aggregateId,
                eventType,
                payload,
                status,
                retryCount,
                createdAt,
                updatedAt,
                publishedAt
        );
    }

    public static OutboxEvent itemActiveChanged(
            String eventId,
            String sku,
            String payload
    ) {
        LocalDateTime now = LocalDateTime.now();
        return new OutboxEvent(
                eventId,
                "item",
                sku,
                OutboxEventType.ITEM_ACTIVE_CHANGED,
                payload,
                OutboxStatus.PENDING,
                0,
                now,
                now,
                null
        );
    }

    public void markSent() {
        this.status = OutboxStatus.SENT;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markFailed() {
        this.status = OutboxStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseRetryCount() {
        this.retryCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean canRetry(int maxRetryCount) {
        return this.retryCount < maxRetryCount;
    }
}