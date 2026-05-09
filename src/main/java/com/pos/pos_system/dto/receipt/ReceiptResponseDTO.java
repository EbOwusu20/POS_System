package com.pos.pos_system.dto.receipt;

import com.pos.pos_system.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ReceiptResponseDTO {
    private Long orderId;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private double totalAmount;
    private String paymentMethod;
    private String paymentProvider;
    private PaymentStatus paymentStatus;
    private String paymentReference;
    private String customerName;
    private List<ReceiptItemDTO> items;

    public ReceiptResponseDTO() {}

    public ReceiptResponseDTO(
            Long orderId,
            LocalDateTime createdAt,
            LocalDateTime paidAt,
            double totalAmount,
            String paymentMethod,
            String paymentProvider,
            PaymentStatus paymentStatus,
            String paymentReference,
            String customerName,
            List<ReceiptItemDTO> items
    ) {
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentProvider = paymentProvider;
        this.paymentStatus = paymentStatus;
        this.paymentReference = paymentReference;
        this.customerName = customerName;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<ReceiptItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItemDTO> items) {
        this.items = items;
    }
}

