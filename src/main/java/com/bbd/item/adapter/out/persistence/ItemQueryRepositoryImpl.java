package com.bbd.item.adapter.out.persistence;


import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Unit;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bbd.item.adapter.out.persistence.QItemJpaEntity.*;

@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemJpaEntity> filter(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ItemJpaEntity> filterName(Pageable pageable, String name) {
        return jpaQueryFactory
                .select(itemJpaEntity)
                .from(itemJpaEntity)
                .where( nameContains(name) )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    // 이름 동적 쿼리
    private BooleanExpression nameContains(String name) {
        // return name != null ? itemJpaEntity.name.contains(name) : null;
        // null 검사를 하려고했지만, 공백이 들어올 위험이 있기 때문에 StringUtil 중에 hasText 즉, 공백이 없는 제대로 된 문자인지 확인
        // public static boolean hasText(@Nullable String str) { return str != null && !str.isBlank(); } 으로 구현 되어있음
        return StringUtils.hasText(name) ? itemJpaEntity.name.contains(name) : null;
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
