package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Product;
import com.student.ecommerce.student.enums.ProductStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByShopId(String shopId);
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    @Query("SELECT p FROM Product p WHERE " +
           "(:keyword IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:categoryId IS NULL OR p.categoryId = :categoryId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "p.status = 'ACTIVE'")
    List<Product> findProductsAdvanced(@Param("keyword") String keyword,
                                      @Param("categoryId") String categoryId,
                                      @Param("minPrice") Double minPrice,
                                      @Param("maxPrice") Double maxPrice,
                                      Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.categoryId IN :categoryIds AND p.status = 'ACTIVE'")
    List<Product> findByCategoryIds(@Param("categoryIds") List<String> categoryIds);
}
