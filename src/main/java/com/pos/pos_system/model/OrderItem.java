package com.pos.pos_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
     @Getter
     @Setter
    @ManyToOne
    private Product product;


     @Getter
     @Setter
    private int quantity;
     @Getter
     @Setter
    private double price;
     @Getter
     @Setter
    @ManyToOne
     @JoinColumn(name = "order_id" , referencedColumnName = "id")
    @JsonBackReference
    private Order order;

    // Getters and Setters
}