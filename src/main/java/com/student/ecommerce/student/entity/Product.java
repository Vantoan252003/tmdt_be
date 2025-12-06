package com.student.ecommerce.student.entity;

import com.student.ecommerce.student.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_shop_id", columnList = "shop_id"),
        @Index(name = "idx_category_id", columnList = "category_id"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_price", columnList = "price")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id", length = 36)
    private String productId;

    @Column(name = "shop_id", nullable = false, length = 36)
    private String shopId;

    @Column(name = "category_id", nullable = false, length = 36)
    private String categoryId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "sold_quantity")
    private Integer soldQuantity = 0;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "total_reviews")
    private Integer totalReviews = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(precision = 8, scale = 2)
    private BigDecimal weight;

    // Product Images (merged from ProductImage entity)
    @Column(name = "main_image_url", length = 500)
    private String mainImageUrl;

    @Column(name = "image_url_1", length = 500)
    private String imageUrl1;

    @Column(name = "image_url_2", length = 500)
    private String imageUrl2;

    @Column(name = "image_url_3", length = 500)
    private String imageUrl3;

    @Column(name = "image_url_4", length = 500)
    private String imageUrl4;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (productId == null) {
            productId = java.util.UUID.randomUUID().toString();
        }
    }
}
