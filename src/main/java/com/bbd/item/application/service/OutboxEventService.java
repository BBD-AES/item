package com.bbd.item.application.service;

import com.bbd.item.application.port.out.ItemEventPublisher;
import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxStatus;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OutboxEventService {

    private final OutboxEventPort outboxEventPort;
    private final ItemEventPublisher itemEventPublisher;

    public void publishPendingEvent(){
        // 1. Event 상태가 PENDING 인 이벤트 리스트로 조회
        List<OutboxEvent> outboxEvents = outboxEventPort.getOutboxEvents(OutboxStatus.PENDING);

        // 2. outboxEvents 리스트 돌면서 성공 or 실패 처리
        for(OutboxEvent outboxEvent : outboxEvents){
            try{
                // 1. Kafka이벤트 발행 시도
                itemEventPublisher.publish(outboxEvent);

                // 2. DB 이벤트 상태 변경


            } catch (Exception e){
                // 1. DB 시도 회수 증가

            }

        }


    }



}
