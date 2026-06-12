package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.CreateItemCommand;

public interface ItemUseCaseCreate {
    void create(CreateItemCommand req);
}
