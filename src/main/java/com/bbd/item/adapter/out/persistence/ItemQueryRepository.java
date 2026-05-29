package com.bbd.item.adapter.out.persistence;

import com.bbd.item.application.port.in.dto.GetItemFilterCommand;

import java.util.List;

public interface ItemQueryRepository {

    List<ItemJpaEntity> filter(GetItemFilterCommand getItemFilterCommand);

    List<ItemJpaEntity> filterName(String name);

}
