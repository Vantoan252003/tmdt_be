package com.student.ecommerce.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.ecommerce.student.dto.AddToCartRequest;
import com.student.ecommerce.student.dto.ApiResponse;
import com.student.ecommerce.student.dto.CartItemResponse;
import com.student.ecommerce.student.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getCart(Authentication authentication) {
        String userId = authentication.getName(); // email from JWT
        List<CartItemResponse> items = cartService.getCartItems(userId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartItemResponse>> addToCart(
            Authentication authentication,
            @RequestBody AddToCartRequest request) {
        try {
            String userId = authentication.getName();
            CartItemResponse item = cartService.addToCart(userId, request);
            return ResponseEntity.ok(ApiResponse.success("Added to cart successfully", item));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartItemResponse>> updateCartItem(
            Authentication authentication,
            @PathVariable String cartItemId,
            @RequestParam Integer quantity) {
        try {
            String userId = authentication.getName();
            CartItemResponse item = cartService.updateCartItem(userId, cartItemId, quantity);
            return ResponseEntity.ok(ApiResponse.success("Cart item updated successfully", item));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(
            Authentication authentication,
            @PathVariable String cartItemId) {
        try {
            String userId = authentication.getName();
            cartService.removeFromCart(userId, cartItemId);
            return ResponseEntity.ok(ApiResponse.success("Item removed from cart", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication authentication) {
        try {
            String userId = authentication.getName();
            cartService.clearCart(userId);
            return ResponseEntity.ok(ApiResponse.success("Cart cleared successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
