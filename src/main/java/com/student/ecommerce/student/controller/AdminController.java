package com.student.ecommerce.student.controller;

import com.student.ecommerce.student.dto.ApiResponse;
import com.student.ecommerce.student.entity.*;
import com.student.ecommerce.student.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // User Management
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllUsers()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getUserById(id)));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createUser(user)));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable String id, @RequestBody User user) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateUser(id, user)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    // Category Management
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllCategories()));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getCategoryById(id)));
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createCategory(category)));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable String id, @RequestBody Category category) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateCategory(id, category)));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable String id) {
        adminService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }

    // Shop Management
    @GetMapping("/shops")
    public ResponseEntity<ApiResponse<List<Shop>>> getAllShops() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllShops()));
    }

    @GetMapping("/shops/{id}")
    public ResponseEntity<ApiResponse<Shop>> getShopById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getShopById(id)));
    }

    @PutMapping("/shops/{id}")
    public ResponseEntity<ApiResponse<Shop>> updateShop(@PathVariable String id, @RequestBody Shop shop) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateShop(id, shop)));
    }

    @DeleteMapping("/shops/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShop(@PathVariable String id) {
        adminService.deleteShop(id);
        return ResponseEntity.ok(ApiResponse.success("Shop deleted successfully", null));
    }

    // Product Management
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllProducts()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getProductById(id)));
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createProduct(product)));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateProduct(id, product)));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }

    // Order Management
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllOrders()));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getOrderById(id)));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(@PathVariable String id, @RequestBody Order order) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateOrderStatus(id, order)));
    }

    // Banner Management
    @GetMapping("/banners")
    public ResponseEntity<ApiResponse<List<Banner>>> getAllBanners() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllBanners()));
    }

    @GetMapping("/banners/{id}")
    public ResponseEntity<ApiResponse<Banner>> getBannerById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getBannerById(id)));
    }

    @PostMapping("/banners")
    public ResponseEntity<ApiResponse<Banner>> createBanner(@RequestBody Banner banner) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createBanner(banner)));
    }

    @PutMapping("/banners/{id}")
    public ResponseEntity<ApiResponse<Banner>> updateBanner(@PathVariable String id, @RequestBody Banner banner) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateBanner(id, banner)));
    }

    @DeleteMapping("/banners/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable String id) {
        adminService.deleteBanner(id);
        return ResponseEntity.ok(ApiResponse.success("Banner deleted successfully", null));
    }

    // Address Management
    @GetMapping("/addresses")
    public ResponseEntity<ApiResponse<List<Address>>> getAllAddresses() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllAddresses()));
    }

    @GetMapping("/addresses/user/{userId}")
    public ResponseEntity<ApiResponse<List<Address>>> getAddressesByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAddressesByUserId(userId)));
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable String id) {
        adminService.deleteAddress(id);
        return ResponseEntity.ok(ApiResponse.success("Address deleted successfully", null));
    }

    // Product Variant Management
    @GetMapping("/product-variants/product/{productId}")
    public ResponseEntity<ApiResponse<List<ProductVariant>>> getProductVariants(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getProductVariants(productId)));
    }

    @PostMapping("/product-variants")
    public ResponseEntity<ApiResponse<ProductVariant>> createProductVariant(@RequestBody ProductVariant variant) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createProductVariant(variant)));
    }

    @PutMapping("/product-variants/{id}")
    public ResponseEntity<ApiResponse<ProductVariant>> updateProductVariant(@PathVariable String id, @RequestBody ProductVariant variant) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateProductVariant(id, variant)));
    }

    @DeleteMapping("/product-variants/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductVariant(@PathVariable String id) {
        adminService.deleteProductVariant(id);
        return ResponseEntity.ok(ApiResponse.success("Product variant deleted successfully", null));
    }

    // Product Image Management
    @GetMapping("/product-images/product/{productId}")
    public ResponseEntity<ApiResponse<List<ProductImage>>> getProductImages(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getProductImages(productId)));
    }

    @PostMapping("/product-images")
    public ResponseEntity<ApiResponse<ProductImage>> createProductImage(@RequestBody ProductImage productImage) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createProductImage(productImage)));
    }

    @DeleteMapping("/product-images/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductImage(@PathVariable String id) {
        adminService.deleteProductImage(id);
        return ResponseEntity.ok(ApiResponse.success("Product image deleted successfully", null));
    }

    // Cart Management
    @GetMapping("/carts")
    public ResponseEntity<ApiResponse<List<Cart>>> getAllCarts() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllCarts()));
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<ApiResponse<Cart>> getCartById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getCartById(id)));
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable String id) {
        adminService.deleteCart(id);
        return ResponseEntity.ok(ApiResponse.success("Cart deleted successfully", null));
    }

    @GetMapping("/cart-items/cart/{cartId}")
    public ResponseEntity<ApiResponse<List<CartItem>>> getCartItems(@PathVariable String cartId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getCartItems(cartId)));
    }

    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable String id) {
        adminService.deleteCartItem(id);
        return ResponseEntity.ok(ApiResponse.success("Cart item deleted successfully", null));
    }

    // Order Item Management
    @GetMapping("/order-items/order/{orderId}")
    public ResponseEntity<ApiResponse<List<OrderItem>>> getOrderItems(@PathVariable String orderId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getOrderItems(orderId)));
    }

    // Review Management
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviews() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllReviews()));
    }

    @GetMapping("/reviews/product/{productId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByProductId(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getReviewsByProductId(productId)));
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<Review>> getReviewById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getReviewById(id)));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable String id) {
        adminService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully", null));
    }

    @GetMapping("/review-images/review/{reviewId}")
    public ResponseEntity<ApiResponse<List<ReviewImage>>> getReviewImages(@PathVariable String reviewId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getReviewImages(reviewId)));
    }

    @DeleteMapping("/review-images/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReviewImage(@PathVariable String id) {
        adminService.deleteReviewImage(id);
        return ResponseEntity.ok(ApiResponse.success("Review image deleted successfully", null));
    }

    // Voucher Management
    @GetMapping("/vouchers")
    public ResponseEntity<ApiResponse<List<Voucher>>> getAllVouchers() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllVouchers()));
    }

    @GetMapping("/vouchers/{id}")
    public ResponseEntity<ApiResponse<Voucher>> getVoucherById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getVoucherById(id)));
    }

    @PostMapping("/vouchers")
    public ResponseEntity<ApiResponse<Voucher>> createVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.ok(ApiResponse.success(adminService.createVoucher(voucher)));
    }

    @PutMapping("/vouchers/{id}")
    public ResponseEntity<ApiResponse<Voucher>> updateVoucher(@PathVariable String id, @RequestBody Voucher voucher) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateVoucher(id, voucher)));
    }

    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVoucher(@PathVariable String id) {
        adminService.deleteVoucher(id);
        return ResponseEntity.ok(ApiResponse.success("Voucher deleted successfully", null));
    }

    // Favorite Management
    @GetMapping("/favorites")
    public ResponseEntity<ApiResponse<List<Favorite>>> getAllFavorites() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllFavorites()));
    }

    @GetMapping("/favorites/user/{userId}")
    public ResponseEntity<ApiResponse<List<Favorite>>> getFavoritesByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getFavoritesByUserId(userId)));
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFavorite(@PathVariable String id) {
        adminService.deleteFavorite(id);
        return ResponseEntity.ok(ApiResponse.success("Favorite deleted successfully", null));
    }

    // Search History Management
    @GetMapping("/search-history")
    public ResponseEntity<ApiResponse<List<SearchHistory>>> getAllSearchHistory() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllSearchHistory()));
    }

    @GetMapping("/search-history/user/{userId}")
    public ResponseEntity<ApiResponse<List<SearchHistory>>> getSearchHistoryByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getSearchHistoryByUserId(userId)));
    }

    @DeleteMapping("/search-history/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSearchHistory(@PathVariable String id) {
        adminService.deleteSearchHistory(id);
        return ResponseEntity.ok(ApiResponse.success("Search history deleted successfully", null));
    }
}
