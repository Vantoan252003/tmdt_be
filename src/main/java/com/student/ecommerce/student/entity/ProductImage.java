package com.student.ecommerce.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_images", indexes = {
        @Index(name = "idx_product_id", columnList = "product_id"),
        @Index(name = "idx_is_primary", columnList = "is_primary")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @Column(name = "image_id", length = 36)
    private String imageId;

    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (imageId == null) {
            imageId = java.util.UUID.randomUUID().toString();
        }
    }
}
