package com.bbd.item.adapter.in.web.dto;


import com.bbd.item.domain.model.item.Item;
import com.bbd.item.domain.model.item.SourcingType;
import com.bbd.item.domain.model.item.Unit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponse {

    private String sku; // 부품 식별자

    private String name; // 부품 이름

    private String category; // 분류

    private Unit unit; // 개 , 박스, L 등 단위

    private int safetyStock; // 결품 방지 최소 보유량

    private int unitPrice; // 단가 (덮어 쓰기, 변경 이력은 각 Procurement & Sales & Inventory 가 저장)

    private boolean active; // 활성 여부 (삭제는 없고, 비활성화로 관리)

    private SourcingType sourcingType; // 생산 or 구매

    public ItemResponse(Item item) {
        this.sku = item.getSku();
        this.name = item.getName();
        this.category = item.getCategory().getLabel();
        this.unit = item.getUnit();
        this.safetyStock = item.getSafetyStock();
        this.unitPrice = item.getUnitPrice();
        this.active = item.isActive();
        this.sourcingType = item.getSourcingType();
    }
}
