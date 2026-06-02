package com.bbd.item.adapter.in.web.dto;

import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Unit;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private Category category;

    @NotBlank
    private Unit unit;

    @NotBlank
    private Integer safetyStock;

    @NotBlank
    private Integer unitPrice;

    @NotBlank
    private Boolean active;

}
