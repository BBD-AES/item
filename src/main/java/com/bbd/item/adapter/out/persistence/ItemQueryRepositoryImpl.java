package com.bbd.item.adapter.out.persistence;


import com.bbd.item.adapter.in.web.dto.ItemListSku;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Unit;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bbd.item.adapter.out.persistence.QItemJpaEntity.*;

@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ItemJpaEntity> filterV2(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {


        // 1. 해당 필터를 통해 sku 리스트 조회
        List<String> skuList = jpaQueryFactory.select(itemJpaEntity.sku)
                .from(itemJpaEntity)
                .where(
                        categoryEq(getItemFilterCommand.getCategory()),
                        nameContains(getItemFilterCommand.getName()),
                        activeEq(getItemFilterCommand.getActive())
                )
                .orderBy(getOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        // 2. total count 조회
        Long total = jpaQueryFactory
                .select(itemJpaEntity.count()) // 카운트
                .from(itemJpaEntity)
                .where(
                        categoryEq(getItemFilterCommand.getCategory()),
                        nameContains(getItemFilterCommand.getName()),
                        activeEq(getItemFilterCommand.getActive())
                )
                .fetchOne();

        long totalCount = total == null ? 0 : total;

        // 3. 해당 조건이 없다면 빈 배열 반환
        if(skuList.isEmpty()){
            return new PageImpl<>(List.of(), pageable,totalCount);
        }

        // 4. where in 쿼리를 통해 sku -> ItemJpaEntity 조회
        List<ItemJpaEntity> itemList = jpaQueryFactory
                .select(itemJpaEntity)
                .from(itemJpaEntity)
                .where(itemJpaEntity.sku.in(skuList))
                .fetch();

        // 5. itemList 는 정렬이 없기 때문에 sku 기준으로 Map 으로 변경
        Map<String, ItemJpaEntity> itemMap = itemList.stream()
                .collect(Collectors.toMap(item -> item.getSku(), Function.identity()));

        // 6. 정렬을 유지하기 위한 skuList 기준으로 매핑
        List<ItemJpaEntity> items = skuList.stream()
                .map(sku -> itemMap.get(sku))
                .toList();

        // 결과 반환
        return new PageImpl<>(items, pageable, totalCount);
    }


    @Override
    public Page<ItemJpaEntity> filterName(Pageable pageable, String name) {
        List<ItemJpaEntity> content = jpaQueryFactory
                .select(itemJpaEntity)
                .from(itemJpaEntity)
                .where(nameContains(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(itemJpaEntity.count())
                .where(nameContains(name))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<ItemJpaEntity> findAllIntSku(ItemListSku itemListSku) {
        return jpaQueryFactory
                .select(itemJpaEntity)
                .from(itemJpaEntity)
                .where(itemJpaEntity.sku.in(itemListSku.getSkuList()))
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


    // Sort & 방향 꺼내주기
    // OrderSpecifier : Querydsl에서 ORDER BY 조건을 표현하는 클래스
    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {

        // 이름이 정렬 기준이라면 item name 이 한글이기 떄문에 한글기준 정렬 설정
//        StringExpression nameKo = Expressions.stringTemplate(
//                "{0} COLLATE \"ko-KR-x-icu\"",
//                itemJpaEntity.name
//        );

        // 1. sort 기준, 방향 꺼내기
        Sort.Order order = pageable.getSort()
                .stream()
                .findFirst()
                .orElse(Sort.Order.asc("name"));

        String sortBy = order.getProperty();
        Sort.Direction direction = order.getDirection();

        // 2. name 정렬
        if (sortBy.equals("name")) {
            if (direction.equals(Sort.Direction.ASC)) {
                return new OrderSpecifier<?>[]{
                        itemJpaEntity.name.asc(),
                        itemJpaEntity.sku.asc()
                };
            }

            return new OrderSpecifier<?>[]{
                    itemJpaEntity.name.desc(),
                    itemJpaEntity.sku.asc()
            };
        }

        // 3. sku 정렬
        if (sortBy.equals("sku")) {
            if (direction.equals(Sort.Direction.ASC)) {
                return new OrderSpecifier<?>[]{
                        itemJpaEntity.sku.asc()
                };
            }

            return new OrderSpecifier<?>[]{
                    itemJpaEntity.sku.desc()
            };
        }

        // 4. 허용하지 않는 정렬 기준이면 기본 정렬
        return new OrderSpecifier<?>[]{
                itemJpaEntity.name.asc(),
                itemJpaEntity.sku.asc()
        };
    }

    // 기존 필터 (성능 무거움)
    @Override
    public Page<ItemJpaEntity> filterV1(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {

        List<ItemJpaEntity> content = jpaQueryFactory
                .select(itemJpaEntity)
                .from(itemJpaEntity)
                .where(
                        categoryEq(getItemFilterCommand.getCategory()),
                        activeEq(getItemFilterCommand.getActive())
                )
                .orderBy(getOrderSpecifiers(pageable)) // sort ,  direction 꺼내기
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(itemJpaEntity.count())
                .from(itemJpaEntity)
                .where(
                        categoryEq(getItemFilterCommand.getCategory()),
                        activeEq(getItemFilterCommand.getActive())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }


}
