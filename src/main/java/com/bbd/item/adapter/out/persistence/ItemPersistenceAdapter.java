package com.bbd.item.adapter.out.persistence;

import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * JPAEntity -> Domain 으로 변환
 */
@Component // 빈으로 등록해야 어댑터도 사용가능
@RequiredArgsConstructor
public class ItemPersistenceAdapter implements ItemPersistencePort {

    private final ItemJpaRepository itemJpaRepository;
    private final ItemPersistenceMapper itemPersistenceMapper;

    @Override
    public boolean existBySku(String sku) {
        return itemJpaRepository.existsById(sku);
    }

    @Override
    public void save(Item item) {
        ItemJpaEntity itemJpaEntity = new ItemJpaEntity(
                item.getSku(),
                item.getName(),
                item.getCategory(),
                item.getUnit(),
                item.getSafetyStock(),
                item.getUnitPrice(),
                item.isActive(),
                item.getSourcingType()
        );
        itemJpaRepository.save(itemJpaEntity);
    }

    @Override
    public Optional<Item> findBySku(String sku) {
        Optional<Item> item = itemJpaRepository.findById(sku)
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
        return item;
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return itemJpaRepository.findAll(pageable)
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }

    @Override
    public Page<Item> getFilter(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {
        return itemJpaRepository.filter(pageable, getItemFilterCommand)
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }

    @Override
    public Page<Item> getName(Pageable pageable, GetNameCommand getNameCommand) {
        return itemJpaRepository.filterName(pageable, getNameCommand.getName())
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }
}
