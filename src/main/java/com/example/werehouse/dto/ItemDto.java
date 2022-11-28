package com.example.werehouse.dto;

import com.example.werehouse.model.Shelf;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ItemDto {
    private String name;
    private Long itemsPerPackage; // штук в упаковке
    private Long packagesNumber; // количество упаковок
    private String vendorCode; // артикул
    private String invoice; // номер накладной
    private Double weight; // вес упаковки
    private Long shelfId;
    private Long userId;
}
