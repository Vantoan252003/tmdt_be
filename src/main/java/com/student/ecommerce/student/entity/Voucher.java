package com.student.ecommerce.student.entity;

import com.student.ecommerce.student.enums.DiscountType;
import com.student.ecommerce.student.enums.VoucherStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers", indexes = {
        @Index(name = "idx_shop_id", columnList = "shop_id"),
        @Index(name = "idx_voucher_code", columnList = "voucher_code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @Column(name = "voucher_id", length = 36)
    private String voucherId;

    @Column(name = "shop_id", length = 36)
    private String shopId;

    @Column(name = "voucher_code", unique = true, nullable = false, length = 50)
    private String voucherCode;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false, precision = 12, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "min_order_amount", precision = 12, scale = 2)
    private BigDecimal minOrderAmount = BigDecimal.ZERO;

    @Column(name = "max_discount_amount", precision = 12, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "used_count")
    private Integer usedCount = 0;

    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_to", nullable = false)
    private LocalDateTime validTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoucherStatus status = VoucherStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (voucherId == null) {
            voucherId = java.util.UUID.randomUUID().toString();
        }
    }
}
