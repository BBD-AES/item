package com.bbd.item.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {

    private String sku;

    private String name;

    private Category category;

    private Unit unit;

    private int safetyStock;

    private int unitPrice;

    private boolean Active;


}
