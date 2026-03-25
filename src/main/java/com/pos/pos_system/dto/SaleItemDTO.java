package com.pos.pos_system.dto;

import lombok.Getter;
import lombok.Setter;

public class SaleItemDTO {
    @Getter
    @Setter
    private Long productId;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private  double price;
    @Getter
    @Setter
    private String barcode;

    public SaleItemDTO(){}

    private SaleItemDTO(String name, Long productId, int quantity, double price){
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }
}
