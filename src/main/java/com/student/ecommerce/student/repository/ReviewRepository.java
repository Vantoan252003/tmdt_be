package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByProductId(String productId);
    List<Review> findByShopId(String shopId);
    List<Review> findByUserId(String userId);
}
