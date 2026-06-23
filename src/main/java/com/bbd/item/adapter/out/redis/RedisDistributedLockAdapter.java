package com.bbd.item.adapter.out.redis;

import com.bbd.item.application.port.out.DistributedLockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisDistributedLockAdapter implements DistributedLockPort {

    private static final String PREFIX = "item:lock:";
    private static final String LOCK_VALUE = "locked";

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean tryLock(String key, Duration ttl) {
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(PREFIX + key, LOCK_VALUE, ttl);
        return Boolean.TRUE.equals(acquired);
    }

    @Override
    public void unlock(String key) {
        redisTemplate.delete(PREFIX + key);
    }
}
