package com.bbd.item.getTest;

import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GetTest4 {

    @Autowired
    private OutboxEventPort outboxEventPort;

    @Test
    @DisplayName("PENDING 상태 이벤트 조회")
    public void 테스트1() throws Exception{

        // given & when
        List<OutboxEvent> outboxEvents = outboxEventPort.getOutboxEvents(OutboxStatus.PENDING);

        // then
        if(outboxEvents.size()>0){
            Assertions.assertEquals(OutboxStatus.PENDING, outboxEvents.getFirst().getStatus());
        }

    }

}
