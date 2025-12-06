package com.student.ecommerce.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items", indexes = {
        @Index(name = "idx_cart_id", columnList = "cart_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @Column(name = "cart_item_id", length = 36)
    private String cartItemId;

    @Column(name = "cart_id", nullable = false, length = 36)
    private String cartId;

    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;

    @Column(name = "variant_id", length = 36)
    private String variantId;

    @Column(nullable = false)
    private Integer quantity = 1;

    @CreationTimestamp
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        if (cartItemId == null) {
            cartItemId = java.util.UUID.randomUUID().toString();
        }
    }
}
