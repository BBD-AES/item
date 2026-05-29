package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.CreateItemCommand;

public interface ItemUseCase {

    void create(CreateItemCommand req);

}
