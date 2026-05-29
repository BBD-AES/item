package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.UpdateCommand;
import com.bbd.item.application.port.in.dto.UpdateNameCommand;
import com.bbd.item.application.port.in.dto.UpdatePriceCommand;

public interface ItemUseCaseUpdate {

    void updateName(UpdateNameCommand updateNameCommand);

    void updatePrice(UpdatePriceCommand updatePriceCommand);

    void update(UpdateCommand updateCommand);

}
