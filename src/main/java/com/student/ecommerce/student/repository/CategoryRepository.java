package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByIsActiveTrue();
    List<Category> findByParentCategoryId(String parentCategoryId);
    List<Category> findByParentCategoryIdIsNull();
}
