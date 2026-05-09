package com.pos.pos_system.controller;

import com.pos.pos_system.dto.OrderRequestDTO;
import com.pos.pos_system.dto.receipt.ReceiptResponseDTO;
import com.pos.pos_system.model.Order;
import com.pos.pos_system.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ CREATE ORDER (FROM POS)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // ✅ GET ALL ORDERS
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ✅ GET ORDER BY ID
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/receipt")
    public ReceiptResponseDTO getReceipt(@PathVariable Long id) {
        return orderService.getReceipt(id);
    }
}