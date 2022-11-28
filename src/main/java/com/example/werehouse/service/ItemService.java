package com.example.werehouse.service;

import com.example.werehouse.component.ClientDatabaseContextHolder;
import com.example.werehouse.dto.ItemDto;
import com.example.werehouse.mapper.ItemDtoToItemMapper;
import com.example.werehouse.model.Item;
import com.example.werehouse.model.Shelf;
import com.example.werehouse.repository.ItemRepository;
import com.example.werehouse.repository.ShelfRepository;
import com.example.werehouse.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ShelfRepository shelfRepository;
    private final ItemDtoToItemMapper itemMapper;

    public List<Item> findAllByUserId() {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        List<Item> items = itemRepository.findAllByUserId(AuthUtils.getCurrentUserId());
        ClientDatabaseContextHolder.clear();
        return items;
    }

    public Item findById(Long id) {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item " + id + " doesn't exist in database"));
        ClientDatabaseContextHolder.clear();
        return item;
    }

    public List<Item> findAllByName(String name) {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        List<Item> items = itemRepository.findAllByNameContaining(name);
        ClientDatabaseContextHolder.clear();
        return items;
    }

    public List<Item> findAllByVendorCode(String vendorCode) {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        List<Item> items = itemRepository.findAllByVendorCodeContaining(vendorCode);
        ClientDatabaseContextHolder.clear();
        return items;
    }

    public void deleteById(Long id) {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        itemRepository.deleteById(id);
        ClientDatabaseContextHolder.clear();
    }

    public Item save(ItemDto itemDto) {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        Shelf shelf = Optional.ofNullable(itemDto.getShelfId())
                .flatMap(shelfRepository::findById)
                .orElse(null);
        Item item = itemMapper.itemDtoToItem(itemDto);
        item.setShelf(shelf);
        Item savedItem = itemRepository.save(item);
        ClientDatabaseContextHolder.clear();
        return savedItem;
    }

    public Item update(Long id, ItemDto itemDto) {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item " + id + " doesn't exist in database"));
        itemMapper.updateItem(itemDto, item);
        Shelf shelf = Optional.ofNullable(itemDto.getShelfId())
                .flatMap(shelfRepository::findById)
                .orElse(null);
        item.setShelf(shelf);
        Item savedItem = itemRepository.save(item);
        ClientDatabaseContextHolder.clear();
        return savedItem;
    }
}
