package com.bbd.item.adapter.out.persistence.item;

import com.bbd.item.application.port.in.dto.GetItemsBySkuCommand;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.UpdatePriceCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemQueryRepository {

    // Category , name, active 동적 쿼리
    Page<ItemJpaEntity> filterV2(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    Page<ItemJpaEntity> filterV1(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    Page<ItemJpaEntity> filterName(Pageable pageable, String name);

    List<ItemJpaEntity> findAllIntSku(GetItemsBySkuCommand getItemsBySkuCommand);

    // 아이템 변경 원자처리
    boolean changePrice(UpdatePriceCommand updatePriceCommand);

    // Sku 포함되면 전부 조회
    List<ItemJpaEntity> getAllInSku(GetItemsBySkuCommand getItemsBySkuCommand);

    boolean activate(String sku);

    boolean deactivate(String sku);

}
