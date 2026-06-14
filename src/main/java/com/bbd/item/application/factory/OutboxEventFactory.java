package com.bbd.item.application.factory;

import com.bbd.item.adapter.out.kafka.ItemKafkaRequest;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxEventType;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxEventFactory {

    private final ObjectMapper objectMapper;

    public OutboxEvent itemPriceChanged(String sku, Integer unitPrice) {
        String eventId = UUID.randomUUID().toString();
        Instant occurredAt = Instant.now();

        ItemKafkaRequest message = new ItemKafkaRequest(
                eventId,
                "item",
                OutboxEventType.ITEM_PRICE_CHANGED.name(),
                occurredAt.toString(),
                sku,
                unitPrice
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
}