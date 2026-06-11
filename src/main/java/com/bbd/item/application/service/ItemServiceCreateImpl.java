package com.bbd.item.application.service;


import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceCreateImpl implements ItemUseCaseCreate {

    private final ItemPersistencePort itemPersistencePort;

    @Override
    public void create(CreateItemCommand req) {
        // TODO : 사용자의 권한에 따라서 생성 못하게 막아야함 (나중에 유저 토큰 들어오면 하자)

        // 이미 존재하는지 확인
        if(itemPersistencePort.existBySku(req.getSku())){
            throw new ApiException(ErrorCode.ITEM_SKU_CONFLICT);
        }

        Item item = new Item(
                req.getSku(),
                req.getName(),
                req.getCategory(),
                req.getUnit(),
                req.getSafetyStock(),
                req.getUnitPrice(),
                req.getActive(),
                req.getSourcingType()
        );

        // 저장
        itemPersistencePort.save(item);
    }

}
