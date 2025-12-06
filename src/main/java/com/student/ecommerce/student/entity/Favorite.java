package com.student.ecommerce.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", 
    uniqueConstraints = @UniqueConstraint(name = "unique_user_product", columnNames = {"user_id", "product_id"}),
    indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @Column(name = "favorite_id", length = 36)
    private String favoriteId;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (favoriteId == null) {
            favoriteId = java.util.UUID.randomUUID().toString();
        }
    }
}
