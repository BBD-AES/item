package com.bbd.item.adapter.out.persistence;


import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Unit;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bbd.item.adapter.out.persistence.QItemJpaEntity.*;

@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemJpaEntity> filter(GetItemFilterCommand getItemFilterCommand) {
        return jpaQueryFactory
                .select(itemJpaEntity)
                .from(itemJpaEntity)
                .where(
                        categoryEq(getItemFilterCommand.getCategory()),
                        activeEq(getItemFilterCommand.getActive()),
                        unitEq(getItemFilterCommand.getUnit()),
                        priceGoe(getItemFilterCommand.getMinPrice()),
                        priceLoe(getItemFilterCommand.getMaxPrice())
                )
                .fetch();
    }

    // 카테고리 필터
    private BooleanExpression categoryEq(Category category) {
        return category != null ? itemJpaEntity.category.eq(category) : null;
    }

    // 활동 상태 필터
    private BooleanExpression activeEq(Boolean active) {
        return active != null ? itemJpaEntity.active.eq(active) : null;
    }

    // 단품, 세트 필터
    private BooleanExpression unitEq(Unit unit) {
        return unit != null ? itemJpaEntity.unit.eq(unit) : null;
    }

    // 최소 가격 필터
    private BooleanExpression priceGoe(Integer price) {
        return price != null ? itemJpaEntity.unitPrice.goe(price) : null;
    }

    // 최대 가격 필터
    private BooleanExpression priceLoe(Integer price) {
        return price != null ? itemJpaEntity.unitPrice.loe(price) : null;
    }


    /**
     *     private Category category;
     *
     *     private Boolean active;
     *
     *     private Unit unit;
     *
     *     private Integer minPrice;
     *
     *     private Integer maxPrice;
     */
}
