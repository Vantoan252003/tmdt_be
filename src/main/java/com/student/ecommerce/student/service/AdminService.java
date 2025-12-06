package com.student.ecommerce.student.service;

import com.student.ecommerce.student.entity.*;
import com.student.ecommerce.student.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    // User Management
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String id, User user) {
        User existingUser = getUserById(id);
        existingUser.setEmail(user.getEmail());
        existingUser.setFullName(user.getFullName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAvatarUrl(user.getAvatarUrl());
        existingUser.setRole(user.getRole());
        existingUser.setStatus(user.getStatus());
        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // Category Management
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(String id, Category category) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setParentCategoryId(category.getParentCategoryId());
        existingCategory.setImageUrl(category.getImageUrl());
        existingCategory.setIsActive(category.getIsActive());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    // Shop Management
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop getShopById(String id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
    }

    public Shop updateShop(String id, Shop shop) {
        Shop existingShop = getShopById(id);
        existingShop.setShopName(shop.getShopName());
        existingShop.setDescription(shop.getDescription());
        existingShop.setLogoUrl(shop.getLogoUrl());
        existingShop.setBannerUrl(shop.getBannerUrl());
        existingShop.setAddress(shop.getAddress());
        existingShop.setPhoneNumber(shop.getPhoneNumber());
        existingShop.setStatus(shop.getStatus());
        return shopRepository.save(existingShop);
    }

    public void deleteShop(String id) {
        shopRepository.deleteById(id);
    }

    // Product Management
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = getProductById(id);
        existingProduct.setShopId(product.getShopId());
        existingProduct.setCategoryId(product.getCategoryId());
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscountPercentage(product.getDiscountPercentage());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setWeight(product.getWeight());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    // Order Management
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order updateOrderStatus(String id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setStatus(order.getStatus());
        existingOrder.setPaymentStatus(order.getPaymentStatus());
        return orderRepository.save(existingOrder);
    }

    // Banner Management
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    public Banner getBannerById(String id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }

    public Banner createBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    public Banner updateBanner(String id, Banner banner) {
        Banner existingBanner = getBannerById(id);
        existingBanner.setTitle(banner.getTitle());
        existingBanner.setImageUrl(banner.getImageUrl());
        existingBanner.setLinkUrl(banner.getLinkUrl());
        existingBanner.setDisplayOrder(banner.getDisplayOrder());
        existingBanner.setIsActive(banner.getIsActive());
        existingBanner.setValidFrom(banner.getValidFrom());
        existingBanner.setValidTo(banner.getValidTo());
        return bannerRepository.save(existingBanner);
    }

    public void deleteBanner(String id) {
        bannerRepository.deleteById(id);
    }

    // Address Management
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> getAddressesByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address getAddressById(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public void deleteAddress(String id) {
        addressRepository.deleteById(id);
    }

    // Product Image Management
    public List<ProductImage> getProductImages(String productId) {
        return productImageRepository.findByProductId(productId);
    }

    public ProductImage createProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public void deleteProductImage(String id) {
        productImageRepository.deleteById(id);
    }

    // Product Variant Management
    public List<ProductVariant> getProductVariants(String productId) {
        return productVariantRepository.findByProductId(productId);
    }

    public ProductVariant createProductVariant(ProductVariant variant) {
        return productVariantRepository.save(variant);
    }

    public ProductVariant updateProductVariant(String id, ProductVariant variant) {
        ProductVariant existing = productVariantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variant not found"));
        existing.setVariantName(variant.getVariantName());
        existing.setPrice(variant.getPrice());
        existing.setStockQuantity(variant.getStockQuantity());
        existing.setSku(variant.getSku());
        return productVariantRepository.save(existing);
    }

    public void deleteProductVariant(String id) {
        productVariantRepository.deleteById(id);
    }

    // Cart Management
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(String id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Transactional
    public void deleteCart(String id) {
        cartItemRepository.deleteByCartId(id);
        cartRepository.deleteById(id);
    }

    // Cart Item Management
    public List<CartItem> getCartItems(String cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    public void deleteCartItem(String id) {
        cartItemRepository.deleteById(id);
    }

    // Order Item Management
    public List<OrderItem> getOrderItems(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    // Review Management
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    public Review getReviewById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    // Review Image Management
    public List<ReviewImage> getReviewImages(String reviewId) {
        return reviewImageRepository.findByReviewId(reviewId);
    }

    public void deleteReviewImage(String id) {
        reviewImageRepository.deleteById(id);
    }

    // Voucher Management
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher getVoucherById(String id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found"));
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Voucher updateVoucher(String id, Voucher voucher) {
        Voucher existing = getVoucherById(id);
        existing.setVoucherCode(voucher.getVoucherCode());
        existing.setDescription(voucher.getDescription());
        existing.setDiscountType(voucher.getDiscountType());
        existing.setDiscountValue(voucher.getDiscountValue());
        existing.setMinOrderAmount(voucher.getMinOrderAmount());
        existing.setMaxDiscountAmount(voucher.getMaxDiscountAmount());
        existing.setUsageLimit(voucher.getUsageLimit());
        existing.setValidFrom(voucher.getValidFrom());
        existing.setValidTo(voucher.getValidTo());
        existing.setStatus(voucher.getStatus());
        return voucherRepository.save(existing);
    }

    public void deleteVoucher(String id) {
        voucherRepository.deleteById(id);
    }

    // Favorite Management
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public List<Favorite> getFavoritesByUserId(String userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public void deleteFavorite(String id) {
        favoriteRepository.deleteById(id);
    }

    // Search History Management
    public List<SearchHistory> getAllSearchHistory() {
        return searchHistoryRepository.findAll();
    }

    public List<SearchHistory> getSearchHistoryByUserId(String userId) {
        return searchHistoryRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void deleteSearchHistory(String id) {
        searchHistoryRepository.deleteById(id);
    }
}
