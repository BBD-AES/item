package com.bbd.item.adapter.in.web.dto;

import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.SourcingType;
import com.bbd.item.domain.model.item.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {

    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    @NotNull
    private Category category;

    @NotNull
    private Unit unit;

    @NotNull
    private Integer safetyStock;

    @NotNull
    private Integer unitPrice;

    @NotNull
    private Boolean active;

    @NotNull
    private SourcingType sourcingType;

}
