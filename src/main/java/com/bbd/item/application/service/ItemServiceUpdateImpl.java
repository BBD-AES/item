package com.bbd.item.application.service;

import com.bbd.item.application.factory.OutboxEventFactory;
import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.UpdateCommand;
import com.bbd.item.application.port.in.dto.UpdateNameCommand;
import com.bbd.item.application.port.in.dto.UpdatePriceCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.application.port.out.OutboxEventPort;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.domain.model.outbox.OutboxEvent;
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
    private final OutboxEventPort outboxEventPort;
    private final OutboxEventFactory outboxEventFactory;

    @Override
    public void updatePrice(UpdatePriceCommand updatePriceCommand) {

        // 아이템 가격 변경
        boolean updated = itemPersistencePort.changePrice(updatePriceCommand);

        // 업데이트 실패 시
        if(!updated){
            throw new ApiException(ErrorCode.ITEM_UPDATE_PRICE_FAIL);
        }

        // 이벤트 DB 저장
        OutboxEvent outboxEvent = outboxEventFactory.itemPriceChanged(updatePriceCommand.getSku(), updatePriceCommand.getUnitPrice());
        outboxEventPort.save(outboxEvent);
    }

    @Override
    @Transactional
    public void activate(String sku) {
        boolean updated = itemPersistencePort.activate(sku);

        if (!updated) {
            // 존재하지 않는 SKU인지, 이미 활성 상태인지 구분하고 싶으면 추가 조회
            Item item = itemPersistencePort.findBySku(sku)
                    .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND));

            if (item.isActive()) {
                throw new ApiException(ErrorCode.ITEM_ALREADY_ACTIVE);
            }

            throw new ApiException(ErrorCode.ITEM_NOT_UPDATABLE);
        }

        OutboxEvent outboxEvent = outboxEventFactory.itemActiveChanged(sku, true);
        outboxEventPort.save(outboxEvent);
    }

    @Override
    @Transactional
    public void deactivate(String sku) {
        boolean updated = itemPersistencePort.deactivate(sku);

        if (!updated) {
            Item item = itemPersistencePort.findBySku(sku)
                    .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND));

            if (!item.isActive()) {
                throw new ApiException(ErrorCode.ITEM_ALREADY_INACTIVE);
            }

            throw new ApiException(ErrorCode.ITEM_NOT_UPDATABLE);
        }

        OutboxEvent outboxEvent = outboxEventFactory.itemActiveChanged(sku, false);
        outboxEventPort.save(outboxEvent);
    }


    @Override
    public void update(UpdateCommand updateCommand) {
        // 이미 존재하는지 확인
        Item item = itemPersistencePort.findBySku(updateCommand.getSku())
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND));

        // 존재한다면 가격 및 이름 업데이트
        item.change(updateCommand.getName(), updateCommand.getUnitPrice());

        // 저장
        itemPersistencePort.save(item);
    }
}
