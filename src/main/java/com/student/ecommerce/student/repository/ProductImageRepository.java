package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {
    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(String productId);
    List<ProductImage> findByProductId(String productId);
    Optional<ProductImage> findByProductIdAndIsPrimaryTrue(String productId);
}
