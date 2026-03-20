package com.pos.pos_system.model;

import jakarta.persistence.*;

@Entity
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private Long productId;
    private int quantity;
    private  double price;

    private Long saleId;

}