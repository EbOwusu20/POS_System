package com.pos.pos_system.dto;

import lombok.Getter;
import lombok.Setter;

public class OrderItemRequestDTO {
    @Getter
    @Setter
        private Long productId;
    @Getter
    @Setter
        private int quantity;
    @Getter
    @Setter
        private double price;

        // Getters and Setters
}
