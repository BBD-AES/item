package com.bbd.item.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommand {

    private String sku;

    private String name;

    private Integer unitPrice;



}
