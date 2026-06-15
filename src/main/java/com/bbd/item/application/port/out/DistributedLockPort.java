package com.bbd.item.application.port.out;

import java.time.Duration;

/**
 * 분산 락 포트.
 * 여러 인스턴스에서 동시에 실행되면 안 되는 작업(예: 전체 재색인)을 직렬화한다.
 */
public interface DistributedLockPort {

    /**
     * 주어진 키로 락 선점을 시도한다.
     *
     * @param key 락 키 (작업 단위로 고유해야 함)
     * @param ttl 락 자동 만료 시간 (락 보유 중 인스턴스가 죽어도 데드락이 되지 않도록 보장)
     * @return 선점에 성공하면 true, 이미 다른 작업이 보유 중이면 false
     */
    boolean tryLock(String key, Duration ttl);

    /**
     * 락을 해제한다.
     *
     * @param key 락 키
     */
    void unlock(String key);
}
