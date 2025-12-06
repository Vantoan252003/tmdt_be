package com.student.ecommerce.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_history", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory {

    @Id
    @Column(name = "search_id", length = 36)
    private String searchId;

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "search_query", nullable = false)
    private String searchQuery;

    @Column(name = "result_count")
    private Integer resultCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (searchId == null) {
            searchId = java.util.UUID.randomUUID().toString();
        }
    }
}
