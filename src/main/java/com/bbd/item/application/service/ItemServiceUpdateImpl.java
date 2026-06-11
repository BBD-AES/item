package com.bbd.item.application.service;

import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.UpdateCommand;
import com.bbd.item.application.port.in.dto.UpdateNameCommand;
import com.bbd.item.application.port.in.dto.UpdatePriceCommand;
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
public class ItemServiceUpdateImpl implements ItemUseCaseUpdate {

    private final ItemPersistencePort itemPersistencePort;

    @Override
    public void updatePrice(UpdatePriceCommand updatePriceCommand) {
        // TODO : 사용자 권한에 따라서 생성 못하게 막아야함 (애초에 시큐리티단에서 1차 검증하고옴)

        // 이미 존재하는지 확인
        Item item = itemPersistencePort.findBySku(updatePriceCommand.getSku())
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND));

        // 존재한다면 가격 업데이트
        item.changePrice(updatePriceCommand.getUnitPrice());

        // 저장
        itemPersistencePort.save(item);
    }


    /**
     * 밑에 있는 서비스들은 변결 불가
     */
    @Override
    public void updateName(UpdateNameCommand updateNameCommand) {
        // TODO : 사용자 권한에 따라서 생성 못하게 막아야함 (애초에 시큐리티단에서 1차 검증하고옴)

        // 이미 존재하는지 확인
        Item item = itemPersistencePort.findBySku(updateNameCommand.getSku())
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND));


        // 존재하다면 이름 업데이트
        item.changeName(updateNameCommand.getName());

        // 저장
        itemPersistencePort.save(item);

    }

    @Override
    public void update(UpdateCommand updateCommand) {
        // TODO : 사용자 권한에 따라서 생성 못하게 막아야함 (애초에 시큐리티단에서 1차 검증하고옴)

        // 이미 존재하는지 확인
        Item item = itemPersistencePort.findBySku(updateCommand.getSku())
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND));

        // 존재한다면 가격 및 이름 업데이트
        item.change(updateCommand.getName(), updateCommand.getUnitPrice());

        // 저장
        itemPersistencePort.save(item);
    }
}
