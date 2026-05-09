package com.pos.pos_system.controller;

import com.pos.pos_system.dto.payment.PaystackInitializeRequest;
import com.pos.pos_system.dto.payment.PaystackInitializeResponse;
import com.pos.pos_system.dto.payment.PaystackVerifyResponse;
import com.pos.pos_system.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/paystack/initialize")
    public ResponseEntity<PaystackInitializeResponse> initializePaystack(@Valid @RequestBody PaystackInitializeRequest req) {
        PaystackInitializeResponse resp = paymentService.initializePaystack(req.getOrderId(), req.getEmail(), req.getAmountPesewas());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/paystack/verify/{reference}")
    public ResponseEntity<PaystackVerifyResponse> verifyPaystack(@PathVariable String reference) {
        return ResponseEntity.ok(paymentService.verifyPaystack(reference));
    }
}

