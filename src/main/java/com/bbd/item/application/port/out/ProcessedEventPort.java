package com.bbd.item.application.port.out;

public interface ProcessedEventPort {

    boolean tryStart(String eventId); // 시도

    void markDone(String eventId);    // 처리

    void release(String eventId);     // 삭제

}