package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findByCartId(String cartId);
    void deleteByCartId(String cartId);
    Optional<CartItem> findByCartIdAndProductIdAndVariantId(String cartId, String productId, String variantId);
}
