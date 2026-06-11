package com.bbd.item.adapter.out.persistence.item;


import com.bbd.item.domain.model.item.Item;
import org.springframework.stereotype.Component;

// Entity -> Domain 으로 매핑
@Component
public class ItemPersistenceMapper {

    public Item toDomain(ItemJpaEntity itemJpaEntity) {
        return new Item(
                itemJpaEntity.getSku(),
                itemJpaEntity.getName(),
                itemJpaEntity.getCategory(),
                itemJpaEntity.getUnit(),
                itemJpaEntity.getSafetyStock(),
                itemJpaEntity.getUnitPrice(),
                itemJpaEntity.isActive(),
                itemJpaEntity.getSourcingType()
        );
    }


}
