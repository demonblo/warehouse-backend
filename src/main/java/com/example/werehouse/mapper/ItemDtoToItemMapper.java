package com.example.werehouse.mapper;

import com.example.werehouse.dto.ItemDto;
import com.example.werehouse.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemDtoToItemMapper {
    @Mappings({
            @Mapping(target = "shelf", ignore = true)
    })
    Item itemDtoToItem(ItemDto dto);

    @Mappings({
            @Mapping(target = "shelf", ignore = true)
    })
    void updateItem(ItemDto itemDto, @MappingTarget Item item);
}
