package com.bbd.item.adapter.out.persistence;

import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemQueryRepository {

    // Category , name, active 동적 쿼리
    Page<ItemJpaEntity> filterV2(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    Page<ItemJpaEntity> filter(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    Page<ItemJpaEntity> filterName(Pageable pageable, String name);

}
