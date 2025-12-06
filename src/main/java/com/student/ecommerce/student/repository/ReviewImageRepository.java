package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, String> {
    List<ReviewImage> findByReviewId(String reviewId);
}
