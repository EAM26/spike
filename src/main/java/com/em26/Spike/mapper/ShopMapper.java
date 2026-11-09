package com.em26.Spike.mapper;

import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.dto.ShopRequest;
import com.em26.Spike.dto.ShopResponse;
import com.em26.Spike.model.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShopMapper {

    private final ReceiptMapper receiptMapper;

    public Shop toEntity(ShopRequest request) {
        Shop shop = new Shop();
        shop.setName(request.name());
        shop.setCity(request.city());
        shop.setApiKey("Ab1234");

        return shop;
    }

    public ShopResponse toResponse(Shop shop) {
        List<ReceiptResponse> receipts = new ArrayList<>();
        if(!shop.getReceipts().isEmpty())
            receipts = shop.getReceipts().stream()
                    .map(receiptMapper::toResponse)
                    .toList();

        return new ShopResponse(
                shop.getId(),
                shop.getName(),
                receipts
        );
    }
}

