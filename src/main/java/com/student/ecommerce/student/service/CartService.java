package com.student.ecommerce.student.service;

import com.student.ecommerce.student.dto.AddToCartRequest;
import com.student.ecommerce.student.dto.CartItemResponse;
import com.student.ecommerce.student.entity.Cart;
import com.student.ecommerce.student.entity.CartItem;
import com.student.ecommerce.student.entity.Product;
import com.student.ecommerce.student.repository.CartItemRepository;
import com.student.ecommerce.student.repository.CartRepository;
import com.student.ecommerce.student.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getOrCreateCart(String userId) {
        Optional<Cart> existingCart = cartRepository.findByUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        return cartRepository.save(newCart);
    }

    public List<CartItemResponse> getCartItems(String userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
        
        return cartItems.stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            return CartItemResponse.from(cartItem, product);
        }).collect(Collectors.toList());
    }

    @Transactional
    public CartItemResponse addToCart(String userId, AddToCartRequest request) {
        // Validate product exists
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Get or create cart
        Cart cart = getOrCreateCart(userId);

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductIdAndVariantId(
                        cart.getCartId(), 
                        request.getProductId(), 
                        request.getVariantId()
                );

        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Update quantity
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            // Create new cart item
            cartItem = new CartItem();
            cartItem.setCartId(cart.getCartId());
            cartItem.setProductId(request.getProductId());
            cartItem.setVariantId(request.getVariantId());
            cartItem.setQuantity(request.getQuantity());
        }

        cartItem = cartItemRepository.save(cartItem);
        return CartItemResponse.from(cartItem, product);
    }

    @Transactional
    public CartItemResponse updateCartItem(String userId, String cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Verify cart belongs to user
        Cart cart = cartRepository.findById(cartItem.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to cart");
        }

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }

        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);

        Product product = productRepository.findById(cartItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return CartItemResponse.from(cartItem, product);
    }

    @Transactional
    public void removeFromCart(String userId, String cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Verify cart belongs to user
        Cart cart = cartRepository.findById(cartItem.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to cart");
        }

        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart(String userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartId(cart.getCartId());
    }
}
