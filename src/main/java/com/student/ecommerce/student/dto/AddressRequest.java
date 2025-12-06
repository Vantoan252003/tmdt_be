package com.student.ecommerce.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String ward;
    private String district;
    private String city;
    private Boolean isDefault = false;
}