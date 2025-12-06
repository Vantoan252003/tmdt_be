package com.student.ecommerce.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private String shopId;
    private String shippingAddressId;
    private String paymentMethod; // COD, BANK_TRANSFER, etc.
    private String voucherCode;
    private List<OrderItemRequest> items;
    private String note;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {
        private String productId;
        private String variantId;
        private Integer quantity;
        private BigDecimal price;
    }
}
