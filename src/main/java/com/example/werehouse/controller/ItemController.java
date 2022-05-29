package com.example.werehouse.controller;

import com.example.werehouse.model.Item;
import com.example.werehouse.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> getAll() {
        return itemService.findAllByUserId();
    }

    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    @GetMapping(params = {"name"})
    public List<Item> getAllByName(@RequestParam String name) {
        return itemService.findAllByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
    }
}
