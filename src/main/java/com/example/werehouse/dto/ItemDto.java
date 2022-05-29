package com.example.werehouse.dto;

import lombok.Data;

@Data
public class ItemDto {
    private String name;
    private Double length;
    private Double width;
    private Double depth;
    private Double weight;
    private Long shelfId;
    private Long userId;
}
