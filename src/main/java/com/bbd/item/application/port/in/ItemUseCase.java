package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.domain.model.Item;

public interface ItemUseCase {

    void create(CreateItemCommand req);

    Item getItem(String sku);

}
