package com.bbd.item.adapter.out.persistence;

import com.bbd.item.adapter.in.web.dto.ItemListSku;
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
        return itemJpaRepository.findById(sku)
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return itemJpaRepository.findAll(pageable)
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }


    @Override
    public Page<Item> getFilterV2(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {


        // 1. 기본 목록 조회 -> Native Query WITH 사용하기
        if (getItemFilterCommand.getActive() == null && getItemFilterCommand.getCategory() == null && getItemFilterCommand.getName() == null) {
            return itemJpaRepository.getNative(pageable)
                    .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
        }

        // 2. 카테고리/이름/active 등 동적 쿼리 필요시 Querydsl 사용하기
        return itemJpaRepository.filterV2(pageable, getItemFilterCommand)
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }


    @Override
    public Page<Item> getName(Pageable pageable, GetNameCommand getNameCommand) {
        return itemJpaRepository.filterName(pageable, getNameCommand.getName())
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }


    // 느린 버전 필터
    @Override
    public Page<Item> getFilterV1(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {
        return itemJpaRepository.filterV1(pageable, getItemFilterCommand)
                .map( itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity));
    }

    @Override
    public List<Item> getAllInSku(ItemListSku itemListSku) {
        return itemJpaRepository.findAllIntSku(itemListSku)
                .stream()
                .map(itemJpaEntity -> itemPersistenceMapper.toDomain(itemJpaEntity))
                .toList();
    }
}
