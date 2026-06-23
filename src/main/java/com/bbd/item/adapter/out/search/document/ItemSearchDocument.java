package com.bbd.item.adapter.out.search.document;

import com.bbd.item.domain.model.item.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setting(settingPath = "elasticsearch/item-search-settings.json")
@Document(indexName = "bbd-items", createIndex = false)
public class ItemSearchDocument {

    @Id
    private String sku;

    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(
                            suffix = "autocomplete",
                            type = FieldType.Text,
                            analyzer = "autocomplete_index",
                            searchAnalyzer = "autocomplete_search"
                    )
            }
    )
    private String name;

    private boolean active;

    public static ItemSearchDocument from(Item item) {
        return new ItemSearchDocument(
                item.getSku(),
                item.getName(),
                item.isActive()
        );
    }

}