package com.bbd.item.application.port.in.dto;

import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.SourcingType;
import com.bbd.item.domain.model.item.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemCommand {

    private String sku;

    private String name;

    private Category category;

    private Unit unit;

    private Integer safetyStock;

    private Integer unitPrice;

    private Boolean active;

    private SourcingType sourcingType;

}
