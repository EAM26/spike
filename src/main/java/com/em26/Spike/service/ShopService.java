package com.em26.Spike.service;

import com.em26.Spike.dto.ShopRequest;
import com.em26.Spike.dto.ShopResponse;
import com.em26.Spike.mapper.ShopMapper;
import com.em26.Spike.model.Shop;
import com.em26.Spike.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopResponse createShop(ShopRequest request) {
        Shop shop = shopRepository.save(shopMapper.toEntity(request));
        return shopMapper.toResponse(shop);
    }

    public ShopResponse findById(Long id) {
        Shop shop =  shopRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found: " + id));
        return shopMapper.toResponse(shop);
    }
}

