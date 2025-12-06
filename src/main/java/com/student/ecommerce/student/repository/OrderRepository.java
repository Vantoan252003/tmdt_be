package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Order;
import com.student.ecommerce.student.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
    List<Order> findByShopId(String shopId);
    List<Order> findByStatus(OrderStatus status);
    Optional<Order> findByOrderCode(String orderCode);
}
