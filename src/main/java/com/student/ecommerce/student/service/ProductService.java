package com.student.ecommerce.student.service;

import com.student.ecommerce.student.entity.Category;
import com.student.ecommerce.student.entity.Product;
import com.student.ecommerce.student.repository.CategoryRepository;
import com.student.ecommerce.student.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByShop(String shopId) {
        return productRepository.findByShopId(shopId);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword);
    }

    public List<Product> searchProductsAdvanced(String keyword, String categoryId, Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.findProductsAdvanced(keyword, categoryId, minPrice, maxPrice, pageable);
    }

    public List<Product> getProductsByCategoryWithSubcategories(String categoryId) {
        List<String> categoryIds = new ArrayList<>();
        categoryIds.add(categoryId);

        // Add subcategory IDs recursively
        addSubcategoryIds(categoryId, categoryIds);

        return productRepository.findByCategoryIds(categoryIds);
    }

    private void addSubcategoryIds(String parentId, List<String> categoryIds) {
        List<Category> subcategories = categoryRepository.findByParentCategoryId(parentId);
        for (Category subcategory : subcategories) {
            categoryIds.add(subcategory.getCategoryId());
            addSubcategoryIds(subcategory.getCategoryId(), categoryIds);
        }
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        product.setProductId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
