package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.UpdateNameCommand;

public interface ItemUseCaseUpdate {
    void updateName(UpdateNameCommand updateNameCommand);
}
