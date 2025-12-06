package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Shop;
import com.student.ecommerce.student.enums.ShopStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
    Optional<Shop> findBySellerId(String sellerId);
    List<Shop> findByStatus(ShopStatus status);
    List<Shop> findByShopNameContainingIgnoreCase(String shopName);
}
