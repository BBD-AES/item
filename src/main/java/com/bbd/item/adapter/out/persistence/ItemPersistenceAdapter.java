package com.bbd.item.adapter.out.persistence;

import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * JPAEntity -> Domain 으로 변환
 */
@Component // 빈으로 등록해야 어댑터도 사용가능
@RequiredArgsConstructor
public class ItemPersistenceAdapter implements ItemPersistencePort {

    private final ItemJpaRepository itemJpaRepository;

    @Override
    public void save(Item item) {

    }

}
