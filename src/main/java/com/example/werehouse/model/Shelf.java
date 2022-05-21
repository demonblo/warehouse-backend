package com.example.werehouse.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@Entity
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shelf_id_gen")
    @SequenceGenerator(name="shelf_id_gen", sequenceName = "shelf_id_seq", allocationSize = 1)
    private Long id;
    private String number;
    private Double maxWeight;
    private Double freeWeight;
}
