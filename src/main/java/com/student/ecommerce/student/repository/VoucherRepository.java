package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Voucher;
import com.student.ecommerce.student.enums.VoucherStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    Optional<Voucher> findByVoucherCode(String voucherCode);
    List<Voucher> findByShopId(String shopId);
    List<Voucher> findByStatus(VoucherStatus status);
}
