package com.bbd.item.application.port.in.dto;

import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.SourcingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetItemFilterCommand {

    private String name;

    private Category category;

    private Boolean active;

    private SourcingType sourcingType;

}
