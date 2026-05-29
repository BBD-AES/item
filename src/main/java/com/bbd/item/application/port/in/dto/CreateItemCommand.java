package com.bbd.item.application.port.in.dto;

import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Unit;
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

}
