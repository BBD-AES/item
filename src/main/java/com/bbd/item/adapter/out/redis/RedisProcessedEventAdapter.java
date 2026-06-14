package com.bbd.item.adapter.out.redis;

import com.bbd.item.application.port.out.ProcessedEventPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisProcessedEventAdapter implements ProcessedEventPort {

    private static final String PREFIX = "item:processed-event:";
    private static final Duration PROCESSING_TTL = Duration.ofMinutes(10);
    private static final Duration DONE_TTL = Duration.ofDays(2);
    private final StringRedisTemplate redisTemplate;

    // 장애 복구를 위해 TTL 짧게, 처리중으로 표기
    @Override
    public boolean tryStart(String eventId) {
        return redisTemplate.opsForValue().setIfAbsent(PREFIX + eventId, "진행중",  PROCESSING_TTL);
    }

    // 멱등성 챙기기 위해 TTL 2일 및 완료 표기
    @Override
    public void markDone(String eventId) {
        redisTemplate.opsForValue().set(PREFIX+eventId, "처리 완료",  DONE_TTL);
    }

    // 삭제 하기
    @Override
    public void release(String eventId) {
        redisTemplate.delete(PREFIX + eventId);
    }
}
