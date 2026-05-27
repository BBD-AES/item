package com.bbd.item.domain.model;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    private String sku;

    private String name;

    private String category;

    private String unit;

    private int safetyStock;

    private int unitPrice;

    private boolean active;

    public Item(String sku, String name, String category, String unit, int safetyStock, int unitPrice, boolean active) {
        validate(sku, name, safetyStock, unitPrice); // null 혹은 공백 값 검사
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.safetyStock = safetyStock;
        this.unitPrice = unitPrice;
        this.active = active;
    }

    private void validate(String sku, String name, int safetyStock, int unitPrice) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("상품 SKU는 필수입니다.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }

        if (safetyStock < 0) {
            throw new IllegalArgumentException("안전 재고는 0 이상이어야 합니다.");
        }

        if (unitPrice < 0) {
            throw new IllegalArgumentException("상품 가격은 0 이상이어야 합니다.");
        }
    }

    // 가격 변경
    public void changePrice(int unitPrice) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("상품 가격은 0 이상이어야 합니다.");
        }
        this.unitPrice = unitPrice;
    }

    // 판매 게시
    public void activate() {
        this.active = true;
    }

    // 판매 중지
    public void deactivate() {
        this.active = false;
    }

}
