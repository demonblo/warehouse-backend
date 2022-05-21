package com.example.werehouse.repo;

import com.example.werehouse.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

//    @Quary("Select i From Item i where i.name")
}
