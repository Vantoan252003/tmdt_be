package com.student.ecommerce.student.controller;

import com.student.ecommerce.student.dto.AddressRequest;
import com.student.ecommerce.student.dto.ApiResponse;
import com.student.ecommerce.student.entity.Address;
import com.student.ecommerce.student.entity.User;
import com.student.ecommerce.student.service.AddressService;
import com.student.ecommerce.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Address>>> getUserAddresses(Authentication authentication) {
        String userId = getUserIdFromAuthentication(authentication);
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(addresses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> getAddressById(@PathVariable String id, Authentication authentication) {
        String userId = getUserIdFromAuthentication(authentication);
        return addressService.getAddressById(id)
                .filter(address -> address.getUserId().equals(userId)) // Ensure user owns the address
                .map(address -> ResponseEntity.ok(ApiResponse.success(address)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/default")
    public ResponseEntity<ApiResponse<Address>> getDefaultAddress(Authentication authentication) {
        String userId = getUserIdFromAuthentication(authentication);
        return addressService.getDefaultAddress(userId)
                .map(address -> ResponseEntity.ok(ApiResponse.success(address)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Address>> createAddress(
            Authentication authentication,
            @RequestBody AddressRequest request) {
        try {
            String userId = getUserIdFromAuthentication(authentication);

            Address address = new Address();
            address.setUserId(userId);
            address.setRecipientName(request.getRecipientName());
            address.setPhoneNumber(request.getPhoneNumber());
            address.setAddressLine(request.getAddressLine());
            address.setWard(request.getWard());
            address.setDistrict(request.getDistrict());
            address.setCity(request.getCity());
            address.setIsDefault(request.getIsDefault());

            Address created = addressService.createAddress(address);
            return ResponseEntity.ok(ApiResponse.success("Address created successfully", created));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> updateAddress(
            @PathVariable String id,
            Authentication authentication,
            @RequestBody AddressRequest request) {
        try {
            String userId = getUserIdFromAuthentication(authentication);

            // Verify the address belongs to the user
            Address existingAddress = addressService.getAddressById(id)
                    .filter(address -> address.getUserId().equals(userId))
                    .orElseThrow(() -> new RuntimeException("Address not found or doesn't belong to user"));

            Address addressDetails = new Address();
            addressDetails.setRecipientName(request.getRecipientName());
            addressDetails.setPhoneNumber(request.getPhoneNumber());
            addressDetails.setAddressLine(request.getAddressLine());
            addressDetails.setWard(request.getWard());
            addressDetails.setDistrict(request.getDistrict());
            addressDetails.setCity(request.getCity());
            addressDetails.setIsDefault(request.getIsDefault());

            Address updated = addressService.updateAddress(id, addressDetails);
            return ResponseEntity.ok(ApiResponse.success("Address updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable String id, Authentication authentication) {
        try {
            String userId = getUserIdFromAuthentication(authentication);

            // Verify the address belongs to the user
            Address address = addressService.getAddressById(id)
                    .filter(addr -> addr.getUserId().equals(userId))
                    .orElseThrow(() -> new RuntimeException("Address not found or doesn't belong to user"));

            addressService.deleteAddress(id);
            return ResponseEntity.ok(ApiResponse.success("Address deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}/default")
    public ResponseEntity<ApiResponse<Address>> setDefaultAddress(@PathVariable String id, Authentication authentication) {
        try {
            String userId = getUserIdFromAuthentication(authentication);
            Address updated = addressService.setDefaultAddress(id, userId);
            return ResponseEntity.ok(ApiResponse.success("Default address updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    private String getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email)
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}