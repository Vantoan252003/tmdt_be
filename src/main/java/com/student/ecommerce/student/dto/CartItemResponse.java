package com.student.ecommerce.student.dto;

import com.student.ecommerce.student.entity.CartItem;
import com.student.ecommerce.student.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private String cartItemId;
    private String productId;
    private String productName;
    private String mainImageUrl;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
    private String variantId;

    public static CartItemResponse from(CartItem cartItem, Product product) {
        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setMainImageUrl(product.getMainImageUrl());
        response.setPrice(product.getPrice());
        response.setQuantity(cartItem.getQuantity());
        response.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        response.setVariantId(cartItem.getVariantId());
        return response;
    }
}
