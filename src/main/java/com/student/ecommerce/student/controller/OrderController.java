package com.student.ecommerce.student.controller;

import com.student.ecommerce.student.dto.ApiResponse;
import com.student.ecommerce.student.dto.CreateOrderRequest;
import com.student.ecommerce.student.entity.Order;
import com.student.ecommerce.student.enums.OrderStatus;
import com.student.ecommerce.student.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders(Authentication authentication) {
        String userId = authentication.getName();
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ApiResponse<List<Order>>> getShopOrders(@PathVariable String shopId) {
        List<Order> orders = orderService.getShopOrders(shopId);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId)
                .map(order -> ResponseEntity.ok(ApiResponse.success(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{orderCode}")
    public ResponseEntity<ApiResponse<Order>> getOrderByCode(@PathVariable String orderCode) {
        return orderService.getOrderByCode(orderCode)
                .map(order -> ResponseEntity.ok(ApiResponse.success(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(
            Authentication authentication,
            @RequestBody CreateOrderRequest request) {
        try {
            String userId = authentication.getName();
            Order order = orderService.createOrder(userId, request);
            return ResponseEntity.ok(ApiResponse.success("Order created successfully", order));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            Order updated = orderService.updateOrderStatus(orderId, orderStatus);
            return ResponseEntity.ok(ApiResponse.success("Order status updated", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            Authentication authentication,
            @PathVariable String orderId) {
        try {
            String userId = authentication.getName();
            orderService.cancelOrder(orderId, userId);
            return ResponseEntity.ok(ApiResponse.success("Order cancelled successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
