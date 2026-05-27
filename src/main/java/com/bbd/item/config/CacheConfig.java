package com.bbd.item.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
public class CacheConfig {

    @Getter
    @RequiredArgsConstructor
    public enum CacheType {
        // item 넣어두고, 1시간 설정, 최대 100 설정
        ITEM("item", 3600, 100);

        private final String cacheName;
        private final int expiredAfterWrite;
        private final int maximumSize;
    }

    @Bean
    public CacheManager cacheManger() {
        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                .map(cache -> new CaffeineCache(cache.getCacheName(),
                        Caffeine.newBuilder()
                                .recordStats() // 캐시 성능 지표 기록 (선택)
                                .expireAfterWrite(cache.getExpiredAfterWrite(), TimeUnit.SECONDS) // TTL 설정
                                .maximumSize(cache.getMaximumSize()) // 최대 개수 설정 (OOM 방지)
                                .build()))
                .collect(Collectors.toList());

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}