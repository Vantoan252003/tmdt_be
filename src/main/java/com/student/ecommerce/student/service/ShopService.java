package com.student.ecommerce.student.service;

import com.student.ecommerce.student.entity.Shop;
import com.student.ecommerce.student.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Optional<Shop> getShopById(String id) {
        return shopRepository.findById(id);
    }

    public Optional<Shop> getShopBySellerId(String sellerId) {
        return shopRepository.findBySellerId(sellerId);
    }

    public List<Shop> searchShops(String keyword) {
        return shopRepository.findByShopNameContainingIgnoreCase(keyword);
    }

    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public Shop updateShop(String id, Shop shop) {
        if (!shopRepository.existsById(id)) {
            throw new RuntimeException("Shop not found with id: " + id);
        }
        shop.setShopId(id);
        return shopRepository.save(shop);
    }

    public void deleteShop(String id) {
        shopRepository.deleteById(id);
    }
}
