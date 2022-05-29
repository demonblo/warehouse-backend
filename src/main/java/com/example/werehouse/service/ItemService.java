package com.example.werehouse.service;

import com.example.werehouse.component.ClientDatabaseContextHolder;
import com.example.werehouse.model.Item;
import com.example.werehouse.repository.ItemRepository;
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

    public void deleteById(Long id) {
        ClientDatabaseContextHolder.set(AuthUtils.getActiveClientDatabase());
        itemRepository.deleteById(id);
        ClientDatabaseContextHolder.clear();
    }
}
