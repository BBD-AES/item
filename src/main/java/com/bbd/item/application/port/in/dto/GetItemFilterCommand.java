package com.bbd.item.application.port.in.dto;

import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetItemFilterCommand {

    private Category category;

    private Boolean active;

    private Unit unit;

    private Integer minPrice;

    private Integer maxPrice;

}
