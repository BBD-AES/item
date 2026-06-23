package com.bbd.item.application.port.in.dto;

import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class GetItemsBySkuCommand {

    private List<String> skuList;

    public GetItemsBySkuCommand(List<String> skuList) {
        validate(skuList);
        this.skuList = skuList;
    }


    private void validate(List<String> skuList) {
        if(CollectionUtils.isEmpty(skuList)) {
            throw new ApiException(ErrorCode.ITEM_SKU_LIST_REQUIRED);
        }

        if(skuList.size() > 50 ){
            throw new ApiException(ErrorCode.ITEM_SKU_LIST_LIMIT_EXCEEDED);
        }

    }

}
