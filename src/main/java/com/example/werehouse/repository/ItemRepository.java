package com.example.werehouse.repository;

import com.example.werehouse.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByUserId(Long userId);
    List<Item> findAllByNameContaining(String name);
}
