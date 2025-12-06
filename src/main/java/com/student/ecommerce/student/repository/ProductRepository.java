package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Product;
import com.student.ecommerce.student.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByShopId(String shopId);
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}
