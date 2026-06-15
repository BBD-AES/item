package com.bbd.item.application.factory;

import com.bbd.item.adapter.out.kafka.ItemEventType;
import com.bbd.item.adapter.out.kafka.ItemKafkaRequest;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxEventType;
import com.bbd.item.domain.model.outbox.OutboxStatus;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxEventFactory {

    private static final String SOURCE = "item";
    private final ObjectMapper objectMapper;

    public OutboxEvent itemPriceChanged(String sku, Integer unitPrice) {
        String eventId = UUID.randomUUID().toString();

        String occurredAt = Instant.now().toString();

        ItemKafkaRequest message = new ItemKafkaRequest(
                eventId,
                SOURCE,
                ItemEventType.ITEM_PRICE_CHANGED,
                occurredAt,
                sku,
                unitPrice,
                null
        );

        String payload = toJson(message);
        return OutboxEvent.itemPriceChanged(eventId, sku, payload);
    }

    private String toJson(ItemKafkaRequest message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JacksonException e) {
            throw new ApiException(ErrorCode.OUTBOX_EVENT_CREATE_FAIL);
        }
    }

    public OutboxEvent itemActiveChanged(String sku, boolean active) {

        String eventId = UUID.randomUUID().toString();

        String occurredAt = Instant.now().toString();

        ItemKafkaRequest message = new ItemKafkaRequest(
                eventId,
                SOURCE,
                ItemEventType.ITEM_ACTIVE_CHANGED,
                occurredAt,
                sku,
                null,
                active
        );
        String payload = toJson(message);
        return OutboxEvent.itemActiveChanged(eventId, sku, payload);
    }



}