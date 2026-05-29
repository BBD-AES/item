package com.bbd.item.application.service;

import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.UpdateNameCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Item;
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
    public void updateName(UpdateNameCommand updateNameCommand) {
        // TODO : 사용자의 권한에 따라서 생성 못하게 막아야함 (나중에 유저 토큰 들어오면 하자)

        // 이미 존재하는지 확인
        Item item = itemPersistencePort.findBySku(updateNameCommand.getSku())
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUNT));


        // 존재하다면 업데이트
        item.changeName(updateNameCommand.getName());

        // 저장
        itemPersistencePort.save(item);

    }

}
