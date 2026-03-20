package com.pos.pos_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Setter
    @Getter
    private String name;
    @Getter
    @Setter
    private String category;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    @Column(unique = true)
    private String barcode;

    public Product(){};

    public Product( String name, String category, double price, int quantity, String barcode){
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.barcode = barcode;
    }

    // Setters and Getters




}
