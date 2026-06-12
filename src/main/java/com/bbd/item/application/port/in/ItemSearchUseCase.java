package com.bbd.item.application.port.in;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;

import java.util.List;

public interface ItemSearchUseCase {

    void bulkCreate();

    List<ItemAutocompleteResponse> autocomplete(String keyword, int size);

}
