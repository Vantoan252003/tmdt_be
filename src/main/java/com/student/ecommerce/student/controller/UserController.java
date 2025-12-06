package com.student.ecommerce.student.controller;

import com.student.ecommerce.student.dto.ApiResponse;
import com.student.ecommerce.student.dto.ChangePasswordRequest;
import com.student.ecommerce.student.dto.UpdateProfileRequest;
import com.student.ecommerce.student.entity.User;
import com.student.ecommerce.student.service.CloudinaryService;
import com.student.ecommerce.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<User>> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email)
                .map(user -> {
                    // Don't return password
                    user.setPasswordHash(null);
                    return ResponseEntity.ok(ApiResponse.success(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<User>> updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {
        try {
            String email = authentication.getName();
            User updated = userService.updateProfile(email, request);
            updated.setPasswordHash(null); // Don't return password
            return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            Authentication authentication,
            @RequestBody ChangePasswordRequest request) {
        try {
            String email = authentication.getName();
            userService.changePassword(email, request);
            return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<ApiResponse<String>> uploadAvatar(
            @RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(ApiResponse.success("Avatar uploaded successfully", avatarUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to upload avatar: " + e.getMessage()));
        }
    }
}
