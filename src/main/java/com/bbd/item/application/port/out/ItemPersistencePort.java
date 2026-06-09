package com.bbd.item.application.port.out;

import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.domain.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemPersistencePort {

    // 존재하는지 확인
    boolean existBySku(String sku);

    // 새로운거 하나 저장
    void save(Item item);

    // SKU 로 단건 조회
    Optional<Item> findBySku(String sku);

    // 전체 조회
    Page<Item> getAll(Pageable pageable);

    // 필터 조회
    Page<Item> getFilterV2(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    // 필터 조회 느린버전
    Page<Item> getFilterV1(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    // 이름 조회 (포함)
    Page<Item> getName(Pageable pageable, GetNameCommand getNameCommand);
}
