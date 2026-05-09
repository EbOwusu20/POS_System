package com.pos.pos_system.dto.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaystackInitializeRequest {
    @NotNull
    private Long orderId;

    @Email
    @NotBlank
    private String email;

    @Min(1)
    private long amountPesewas; // amount in pesewas (GHS * 100)

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getAmountPesewas() {
        return amountPesewas;
    }

    public void setAmountPesewas(long amountPesewas) {
        this.amountPesewas = amountPesewas;
    }
}

