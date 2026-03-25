package com.pos.pos_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SaleRequestDTO {
    @Getter
    @Setter
    private Long customerId;
    @Getter
    @Setter
    private String paymentMethod;
    @Getter
    @Setter
    private List<SaleItemDTO> items;

    public SaleRequestDTO(){}

    public SaleRequestDTO(Long customerId, String paymentMethod, List<SaleItemDTO> items){
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.items = items;


    }




}
