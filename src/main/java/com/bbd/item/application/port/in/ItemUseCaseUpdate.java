package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.UpdateCommand;
import com.bbd.item.application.port.in.dto.UpdateNameCommand;
import com.bbd.item.application.port.in.dto.UpdatePriceCommand;
import jakarta.validation.constraints.NotBlank;

public interface ItemUseCaseUpdate {

    void updatePrice(UpdatePriceCommand updatePriceCommand);

    void update(UpdateCommand updateCommand);

    void activate(String sku);

    void deactivate(String sku);

}
