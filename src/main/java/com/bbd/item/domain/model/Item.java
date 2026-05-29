package com.bbd.item.domain.model;

import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    private String sku; // 부품 식별자

    private String name; // 부품 이름

    private Category category; // 분류

    private Unit unit; // 개 , 박스, L 등 단위

    private int safetyStock; // 결품 방지 최소 보유량

    private int unitPrice; // 단가 (덮어 쓰기, 변경 이력은 각 Procurement & Sales & Inventory 가 저장)

    private boolean active; // 활성 여부 (삭제는 없고, 비활성화로 관리)

    public Item(String sku, String name, Category category, Unit unit, int safetyStock, int unitPrice, boolean active) {
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
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }

        if (name == null || name.isBlank()) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }

        if (safetyStock < 0) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }

        if (unitPrice < 0) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }
    }

    // 이름 변경
    public void changeName(String newName){
        if (newName == null || newName.isBlank()) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }
        this.name = newName;
    }

    // 가격 변경
    public void changePrice(int unitPrice) {
        if (unitPrice < 0) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }
        this.unitPrice = unitPrice;
    }

    // 이름 및 가격 변경
    public void change(String name, Integer unitPrice) {
        if (name == null || name.isBlank()) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }
        if (unitPrice == null || unitPrice < 0) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR);
        }
        this.name = name;
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
