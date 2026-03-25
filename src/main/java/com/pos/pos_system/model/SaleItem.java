package com.pos.pos_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonBackReference
    private Sale sale;
     @ManyToOne
     @JoinColumn(name = "product_id")
     private Product product;
    private int quantity;
    private  double price;


    public SaleItem() {}
    public SaleItem(Sale sale, Product product, int quantity, double price){
        this.product = product;
        this.sale = sale;
        this.price = price;
        this.quantity= quantity;

    }


}

