package com.example.werehouse.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_gen")
    @SequenceGenerator(name="item_id_gen", sequenceName = "item_id_seq", allocationSize = 1)
    private Long id;
    private String name; // название
    private Long itemsPerPackage; // штук в упаковке
    private Long packagesNumber; // количество упаковок
    private String vendorCode; // артикул
    private String invoice; // номер накладной
    private Double weight; // вес упаковки
    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;
    private Long userId;
}
