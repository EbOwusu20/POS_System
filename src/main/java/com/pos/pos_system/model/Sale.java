package com.pos.pos_system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;


    @Getter
    @Setter
   private double totalAmount;
    @Getter
    @Setter
    private LocalDateTime saleDate;
    @Getter
    @Setter
    private String paymentMethod;
    @Getter
    @Setter
     private LocalDateTime createdAt;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
     @Getter
     @Setter
     @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
     @JsonManagedReference
    private List<SaleItem> items;

     public  Sale(){}

    public Sale( double totalAmount, String paymentMethod, LocalDateTime createdAt, List<SaleItem> items){         this.paymentMethod = paymentMethod;
         this.totalAmount = totalAmount;
         this.items = items;
         this.createdAt = LocalDateTime.now();
    }



}
