package com.pos.pos_system.model;

import com.pos.pos_system.model.Customer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
     @Getter
     @Setter
    @ManyToOne
    private Customer customer;
    @Getter
    @Setter
    private double totalAmount;

     @Getter
     @Setter
    private String paymentMethod;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Getter
    @Setter
    private String paymentProvider;

    @Getter
    @Setter
    @Column(unique = true)
    private String paymentReference;

    @Getter
    @Setter
    private LocalDateTime paidAt;

    @Getter
    @Setter
    private LocalDateTime createdAt;

     @Getter
     @Setter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> items;

    // Getters and Setters

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
