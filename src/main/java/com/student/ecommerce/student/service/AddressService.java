package com.student.ecommerce.student.service;

import com.student.ecommerce.student.entity.Address;
import com.student.ecommerce.student.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    public Optional<Address> getAddressById(String addressId) {
        return addressRepository.findById(addressId);
    }

    public Optional<Address> getDefaultAddress(String userId) {
        return addressRepository.findByUserIdAndIsDefaultTrue(userId);
    }

    @Transactional
    public Address createAddress(Address address) {
        // If this is the first address or marked as default, set it as default
        List<Address> userAddresses = addressRepository.findByUserId(address.getUserId());
        if (userAddresses.isEmpty() || address.getIsDefault()) {
            // Remove default flag from other addresses
            userAddresses.forEach(addr -> addr.setIsDefault(false));
            addressRepository.saveAll(userAddresses);
            address.setIsDefault(true);
        }

        address.setAddressId(UUID.randomUUID().toString());
        return addressRepository.save(address);
    }

    @Transactional
    public Address updateAddress(String addressId, Address addressDetails) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();

            // Update fields
            address.setRecipientName(addressDetails.getRecipientName());
            address.setPhoneNumber(addressDetails.getPhoneNumber());
            address.setAddressLine(addressDetails.getAddressLine());
            address.setWard(addressDetails.getWard());
            address.setDistrict(addressDetails.getDistrict());
            address.setCity(addressDetails.getCity());

            // Handle default address logic
            if (addressDetails.getIsDefault() && !address.getIsDefault()) {
                // Remove default flag from other addresses
                List<Address> userAddresses = addressRepository.findByUserId(address.getUserId());
                userAddresses.forEach(addr -> addr.setIsDefault(false));
                addressRepository.saveAll(userAddresses);
                address.setIsDefault(true);
            } else if (!addressDetails.getIsDefault() && address.getIsDefault()) {
                // If removing default flag, ensure at least one address remains default
                address.setIsDefault(false);
            }

            return addressRepository.save(address);
        }
        throw new RuntimeException("Address not found");
    }

    @Transactional
    public void deleteAddress(String addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();

            // If deleting default address, set another address as default
            if (address.getIsDefault()) {
                List<Address> userAddresses = addressRepository.findByUserId(address.getUserId());
                userAddresses.removeIf(addr -> addr.getAddressId().equals(addressId));
                if (!userAddresses.isEmpty()) {
                    userAddresses.get(0).setIsDefault(true);
                    addressRepository.save(userAddresses.get(0));
                }
            }

            addressRepository.deleteById(addressId);
        }
    }

    @Transactional
    public Address setDefaultAddress(String addressId, String userId) {
        // Remove default flag from all user addresses
        List<Address> userAddresses = addressRepository.findByUserId(userId);
        userAddresses.forEach(addr -> addr.setIsDefault(false));
        addressRepository.saveAll(userAddresses);

        // Set the specified address as default
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            if (address.getUserId().equals(userId)) {
                address.setIsDefault(true);
                return addressRepository.save(address);
            }
        }
        throw new RuntimeException("Address not found or doesn't belong to user");
    }
}