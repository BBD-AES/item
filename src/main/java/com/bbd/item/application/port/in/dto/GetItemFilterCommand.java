package com.bbd.item.application.port.in.dto;

import com.bbd.item.domain.model.Category;
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

}
