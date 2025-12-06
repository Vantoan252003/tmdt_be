package com.student.ecommerce.student.repository;

import com.student.ecommerce.student.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, String> {
    List<Banner> findByIsActiveTrueOrderByDisplayOrderAsc();
}
