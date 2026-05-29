package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.Item;

import java.util.Optional;

public interface ItemPersistencePort {

    // 존재하는지 확인
    boolean existBySku(String sku);

    // 새로운거 하나 저장
    void save(Item item);

    // SKU 로 단건 조회
    Optional<Item> findBySku(String sku);

}
