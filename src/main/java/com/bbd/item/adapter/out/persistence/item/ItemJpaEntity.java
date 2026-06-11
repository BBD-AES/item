package com.bbd.item.adapter.out.persistence.item;

import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.SourcingType;
import com.bbd.item.domain.model.item.Unit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "item")
@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemJpaEntity {



    @Id
    @Column(nullable = false, updatable = false, length = 100)
    private String sku;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unit unit;

    @Column(nullable = false)
    private int safetyStock; // 결품 방지 최소 보유량

    @Column(nullable = false)
    private int unitPrice; // 단가 (덮어 쓰기, 변경 이력은 각 Procurement & Sales & Inventory 가 저장)

    @Column(nullable = false)
    private boolean active; // 활성 여부 (삭제는 없고, 비활성화로 관리)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourcingType sourcingType;

    // JPA 저장용 생성자
    public ItemJpaEntity(
            String sku,
            String name,
            Category category,
            Unit unit,
            int safetyStock,
            int unitPrice,
            boolean active,
            SourcingType sourcingType
    ) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.safetyStock = safetyStock;
        this.unitPrice = unitPrice;
        this.active = active;
        this.sourcingType = sourcingType;
    }

}
