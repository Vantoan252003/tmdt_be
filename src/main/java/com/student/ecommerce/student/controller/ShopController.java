package com.student.ecommerce.student.controller;

import com.student.ecommerce.student.dto.ApiResponse;
import com.student.ecommerce.student.entity.Shop;
import com.student.ecommerce.student.service.CloudinaryService;
import com.student.ecommerce.student.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Shop>>> getAllShops() {
        return ResponseEntity.ok(ApiResponse.success(shopService.getAllShops()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Shop>> getShopById(@PathVariable String id) {
        return shopService.getShopById(id)
                .map(shop -> ResponseEntity.ok(ApiResponse.success(shop)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my-shop")
    public ResponseEntity<ApiResponse<Shop>> getMyShop(Authentication authentication) {
        String email = authentication.getName();
        return shopService.getShopBySellerId(email)
                .map(shop -> ResponseEntity.ok(ApiResponse.success(shop)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Shop>>> searchShops(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success(shopService.searchShops(keyword)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Shop>> createShop(
            Authentication authentication,
            @RequestBody Shop shop) {
        try {
            String email = authentication.getName();
            shop.setSellerId(email);
            Shop created = shopService.createShop(shop);
            return ResponseEntity.ok(ApiResponse.success("Shop created successfully", created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/upload-logo")
    public ResponseEntity<ApiResponse<String>> uploadLogo(@RequestParam("file") MultipartFile file) {
        try {
            String logoUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(ApiResponse.success("Logo uploaded successfully", logoUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to upload logo: " + e.getMessage()));
        }
    }

    @PostMapping("/upload-banner")
    public ResponseEntity<ApiResponse<String>> uploadBanner(@RequestParam("file") MultipartFile file) {
        try {
            String bannerUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(ApiResponse.success("Banner uploaded successfully", bannerUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to upload banner: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Shop>> updateShop(
            Authentication authentication,
            @PathVariable String id,
            @RequestBody Shop shop) {
        try {
            // Verify shop belongs to user
            String email = authentication.getName();
            Shop existingShop = shopService.getShopById(id)
                    .orElseThrow(() -> new RuntimeException("Shop not found"));
            
            if (!existingShop.getSellerId().equals(email)) {
                return ResponseEntity.status(403)
                        .body(ApiResponse.error("You don't have permission to update this shop"));
            }

            Shop updated = shopService.updateShop(id, shop);
            return ResponseEntity.ok(ApiResponse.success("Shop updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShop(
            Authentication authentication,
            @PathVariable String id) {
        try {
            // Verify shop belongs to user
            String email = authentication.getName();
            Shop existingShop = shopService.getShopById(id)
                    .orElseThrow(() -> new RuntimeException("Shop not found"));
            
            if (!existingShop.getSellerId().equals(email)) {
                return ResponseEntity.status(403)
                        .body(ApiResponse.error("You don't have permission to delete this shop"));
            }

            shopService.deleteShop(id);
            return ResponseEntity.ok(ApiResponse.success("Shop deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
