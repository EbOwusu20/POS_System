package com.pos.pos_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class SaleResponseDTO {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private LocalDateTime saleDate;
    @Getter
    @Setter
    private double totalAmount;
    @Getter
    @Setter
    private String paymentMethod;
    @Getter
    @Setter
    private List<SaleItemDTO> items;

    public  SaleResponseDTO(Long id, LocalDateTime saleDate, double totalAmount, String paymentMethod, List<SaleItemDTO> items){
        this.paymentMethod = paymentMethod;
        this.items = items;
        this.saleDate = saleDate;
        this.id = id;
        this.totalAmount = totalAmount;
    }

}
