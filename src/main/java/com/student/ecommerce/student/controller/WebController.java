package com.student.ecommerce.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/users")
    public String adminUsers() {
        return "admin/users";
    }

    @GetMapping("/admin/addresses")
    public String adminAddresses() {
        return "admin/addresses";
    }

    @GetMapping("/admin/categories")
    public String adminCategories() {
        return "admin/categories";
    }

    @GetMapping("/admin/shops")
    public String adminShops() {
        return "admin/shops";
    }

    @GetMapping("/admin/products")
    public String adminProducts() {
        return "admin/products";
    }

    @GetMapping("/admin/product-images")
    public String adminProductImages() {
        return "admin/product-images";
    }

    @GetMapping("/admin/product-variants")
    public String adminProductVariants() {
        return "admin/product-variants";
    }

    @GetMapping("/admin/carts")
    public String adminCarts() {
        return "admin/carts";
    }

    @GetMapping("/admin/orders")
    public String adminOrders() {
        return "admin/orders";
    }

    @GetMapping("/admin/reviews")
    public String adminReviews() {
        return "admin/reviews";
    }

    @GetMapping("/admin/vouchers")
    public String adminVouchers() {
        return "admin/vouchers";
    }

    @GetMapping("/admin/banners")
    public String adminBanners() {
        return "admin/banners";
    }

    @GetMapping("/admin/notifications")
    public String adminNotifications() {
        return "admin/notifications";
    }

    @GetMapping("/admin/favorites")
    public String adminFavorites() {
        return "admin/favorites";
    }

    @GetMapping("/admin/search-history")
    public String adminSearchHistory() {
        return "admin/search-history";
    }
}
