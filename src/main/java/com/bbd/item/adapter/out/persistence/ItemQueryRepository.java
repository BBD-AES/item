package com.bbd.item.adapter.out.persistence;

import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemQueryRepository {

    List<ItemJpaEntity> filter(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    List<ItemJpaEntity> filterName(Pageable pageable, String name);

}
