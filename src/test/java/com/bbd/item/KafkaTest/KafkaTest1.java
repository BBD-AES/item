package com.bbd.item.KafkaTest;

import com.bbd.item.adapter.out.kafka.ItemEventPublisherAdapter;
import com.bbd.item.adapter.out.persistence.outbox.OutboxEventJpaEntity;
import com.bbd.item.adapter.out.persistence.outbox.OutboxEventJpaRepository;
import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

@SpringBootTest
public class KafkaTest1 {

    @Autowired
    private ItemEventPublisherAdapter itemEventPublisherAdapter;

    @Autowired
    private OutboxEventPort outboxEventPort;

    @Test
    @DisplayName("PEDING 상태 조회 후 카프카 이벤트 발행")
    public void 테스트1() throws Exception{

        // given
        List<OutboxEvent> outboxEvents = outboxEventPort.getOutboxEvents(OutboxStatus.PENDING);

        // when
        if(!outboxEvents.isEmpty()){
            itemEventPublisherAdapter.publish(outboxEvents.getFirst());
        }

        System.out.println("Instant.now() = " + Instant.now());


    }


}
