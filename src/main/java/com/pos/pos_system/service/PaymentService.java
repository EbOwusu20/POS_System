package com.pos.pos_system.service;

import com.pos.pos_system.dto.payment.PaystackInitializeResponse;
import com.pos.pos_system.dto.payment.PaystackVerifyResponse;
import com.pos.pos_system.model.Order;
import com.pos.pos_system.model.PaymentStatus;
import com.pos.pos_system.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    private final OrderRepository orderRepository;
    private final PaystackClient paystackClient;

    public PaymentService(OrderRepository orderRepository, PaystackClient paystackClient) {
        this.orderRepository = orderRepository;
        this.paystackClient = paystackClient;
    }

    @Transactional
    public PaystackInitializeResponse initializePaystack(Long orderId, String email, long amountPesewas) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getPaymentReference() == null || order.getPaymentReference().isBlank()) {
            order.setPaymentReference(generateReference(orderId));
        }

        order.setPaymentProvider("PAYSTACK");
        order.setPaymentStatus(PaymentStatus.PENDING);
        orderRepository.save(order);

        return paystackClient.initializePayment(email, amountPesewas, order.getPaymentReference());
    }

    @Transactional
    public PaystackVerifyResponse verifyPaystack(String reference) {
        PaystackVerifyResponse verify = paystackClient.verifyPayment(reference);

        Order order = orderRepository.findByPaymentReference(reference)
                .orElseThrow(() -> new RuntimeException("Order not found for reference: " + reference));

        boolean success = verify != null
                && verify.isStatus()
                && verify.getData() != null
                && "success".equalsIgnoreCase(verify.getData().getStatus());

        if (success) {
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setPaidAt(LocalDateTime.now());
        } else {
            order.setPaymentStatus(PaymentStatus.FAILED);
        }

        orderRepository.save(order);
        return verify;
    }

    private String generateReference(Long orderId) {
        return "order_" + orderId + "_" + UUID.randomUUID();
    }
}

