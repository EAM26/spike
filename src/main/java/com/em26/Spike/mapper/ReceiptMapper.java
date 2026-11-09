package com.em26.Spike.mapper;

import com.em26.Spike.dto.ReceiptRequest;
import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.model.Receipt;
import com.em26.Spike.model.Shop;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public ReceiptResponse toResponse(Receipt receipt) {
        return new ReceiptResponse(
                receipt.getId(),
                receipt.getTotalPrice(),
                receipt.getShop().getName()
        );
    }

    public Receipt toEntity(ReceiptRequest request, Shop shop) {
        Receipt receipt = new Receipt();
        receipt.setShop(shop);
        receipt.setStorageLocation(request.storageLocation());
        receipt.setTotalPrice(request.totalPrice());
        return receipt;
    }
}
