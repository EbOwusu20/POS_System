package com.pos.pos_system.repository;

import com.pos.pos_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByPaymentReference(String paymentReference);
}
