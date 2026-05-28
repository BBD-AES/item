package com.bbd.item.application.port.in;

import com.bbd.item.adapter.in.web.dto.CreateItemRequest;

public interface ItemUseCase {

    void create(CreateItemRequest req);

}
