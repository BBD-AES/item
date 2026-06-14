package com.bbd.item.application.port.in;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.domain.model.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemSearchUseCase {

    void bulkCreate();

    List<ItemAutocompleteResponse> autocomplete(String keyword, int size);

//    Page<Item> search(Pageable pageable, GetItemFilterCommand command);

}
