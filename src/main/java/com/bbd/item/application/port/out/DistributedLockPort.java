package com.bbd.item.application.port.out;

import java.time.Duration;


public interface DistributedLockPort {

    boolean tryLock(String key, Duration ttl);

    void unlock(String key);

}
