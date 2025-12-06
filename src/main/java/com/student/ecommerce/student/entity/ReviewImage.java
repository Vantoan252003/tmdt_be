package com.student.ecommerce.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_images", indexes = {
        @Index(name = "idx_review_id", columnList = "review_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {

    @Id
    @Column(name = "review_image_id", length = 36)
    private String reviewImageId;

    @Column(name = "review_id", nullable = false, length = 36)
    private String reviewId;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (reviewImageId == null) {
            reviewImageId = java.util.UUID.randomUUID().toString();
        }
    }
}
