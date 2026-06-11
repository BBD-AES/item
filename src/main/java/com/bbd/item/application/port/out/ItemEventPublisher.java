package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.outbox.OutboxEvent;

/**
 * DB에서 OutBox 이벤트를 읽어와서 실제 이벤트를 발행하는 퍼블리셔
 */
public interface ItemEventPublisher {

    void publish(OutboxEvent event);

}
