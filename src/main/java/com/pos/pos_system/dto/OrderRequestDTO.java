package com.pos.pos_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderRequestDTO {
    @Getter
    @Setter
    private Long customerId;
    @Getter
    @Setter
    private List<OrderItemRequestDTO> items;
    @Getter
    @Setter
    private double totalAmount;
    @Getter
    @Setter
    private String paymentMethod;

        // Getters and Setters
}
