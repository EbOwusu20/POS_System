package com.pos.pos_system.service;

import com.pos.pos_system.dto.OrderItemRequestDTO;
import com.pos.pos_system.dto.OrderRequestDTO;
import com.pos.pos_system.model.Customer;
import com.pos.pos_system.model.Order;
import com.pos.pos_system.model.OrderItem;
import com.pos.pos_system.model.PaymentStatus;
import com.pos.pos_system.model.Product;
import com.pos.pos_system.repository.CustomerRepository;
import com.pos.pos_system.repository.OrderRepository;
import com.pos.pos_system.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pos.pos_system.dto.receipt.ReceiptItemDTO;
import com.pos.pos_system.dto.receipt.ReceiptResponseDTO;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(OrderRequestDTO request) {

        Order order = new Order();

        // ✅ Set customer
        if (request.getCustomerId() != null) {
            Customer customer = customerRepository
                    .findById(request.getCustomerId())
                    .orElse(null);
            order.setCustomer(customer);
        }

        order.setTotalAmount(request.getTotalAmount());
        order.setPaymentMethod(request.getPaymentMethod());

        if (request.getPaymentMethod() != null && request.getPaymentMethod().equalsIgnoreCase("cash")) {
            order.setPaymentProvider("CASH");
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setPaidAt(LocalDateTime.now());
        } else {
            order.setPaymentStatus(PaymentStatus.PENDING);
        }

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequestDTO itemReq : request.getItems()) {

            Product product = productRepository
                    .findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(itemReq.getPrice());
            item.setOrder(order); // 🔥 VERY IMPORTANT

            items.add(item);
        }

        order.setItems(items);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public ReceiptResponseDTO getReceipt(Long orderId) {
        Order order = getOrderById(orderId);

        List<ReceiptItemDTO> items = order.getItems() == null
                ? List.of()
                : order.getItems().stream()
                .map(i -> new ReceiptItemDTO(
                        i.getProduct() != null ? i.getProduct().getName() : "Item",
                        i.getQuantity(),
                        i.getPrice(),
                        i.getQuantity() * i.getPrice()
                ))
                .collect(Collectors.toList());

        String customerName = order.getCustomer() != null ? order.getCustomer().getName() : "Walk-in";

        return new ReceiptResponseDTO(
                order.getId(),
                order.getCreatedAt(),
                order.getPaidAt(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getPaymentProvider(),
                order.getPaymentStatus(),
                order.getPaymentReference(),
                customerName,
                items
        );
    }
}
