package com.student.ecommerce.student.service;

import com.student.ecommerce.student.dto.CreateOrderRequest;
import com.student.ecommerce.student.entity.Order;
import com.student.ecommerce.student.entity.OrderItem;
import com.student.ecommerce.student.entity.Product;
import com.student.ecommerce.student.enums.OrderStatus;
import com.student.ecommerce.student.enums.PaymentMethod;
import com.student.ecommerce.student.enums.PaymentStatus;
import com.student.ecommerce.student.repository.OrderItemRepository;
import com.student.ecommerce.student.repository.OrderRepository;
import com.student.ecommerce.student.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getShopOrders(String shopId) {
        return orderRepository.findByShopId(shopId);
    }

    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public Optional<Order> getOrderByCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
    }

    @Transactional
    public Order createOrder(String userId, CreateOrderRequest request) {
        // Calculate total
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreateOrderRequest.OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            
            // Check stock
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
            }
            
            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setShopId(request.getShopId());
        order.setOrderCode(generateOrderCode());
        order.setTotalAmount(totalAmount);
        order.setShippingFee(BigDecimal.ZERO); // Calculate based on address
        order.setDiscountAmount(BigDecimal.ZERO); // Apply voucher if provided
        order.setFinalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setShippingAddress(request.getShippingAddressId());
        order.setNote(request.getNote());

        order = orderRepository.save(order);

        // Create order items
        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId()).get();
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(itemRequest.getProductId());
            orderItem.setVariantId(itemRequest.getVariantId());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(itemRequest.getPrice());
            orderItem.setProductName(product.getProductName());
            orderItem.setProductImage(product.getMainImageUrl());
            
            orderItemRepository.save(orderItem);

            // Update product stock
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            product.setSoldQuantity(product.getSoldQuantity() + itemRequest.getQuantity());
            productRepository.save(product);
        }

        return order;
    }

    @Transactional
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to cancel this order");
        }

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Cannot cancel order in current status");
        }

        // Restore product stock
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                product.setSoldQuantity(Math.max(0, product.getSoldQuantity() - item.getQuantity()));
                productRepository.save(product);
            }
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private String generateOrderCode() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ORD" + timestamp + ((int) (Math.random() * 1000));
    }
}
