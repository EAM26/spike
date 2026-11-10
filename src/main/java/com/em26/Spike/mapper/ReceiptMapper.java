package com.em26.Spike.mapper;

import com.em26.Spike.dto.ReceiptRequest;
import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.model.Receipt;
import com.em26.Spike.model.Shop;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public ReceiptResponse toResponse(Receipt receipt) {
        Long userId = null;
        if(receipt.getUser() != null) {
            userId = receipt.getUser().getId();
        }
        return new ReceiptResponse(
                receipt.getId(),
                receipt.getSerialNumber(),
                receipt.getTotalPrice(),
                receipt.getShop().getName(),
                receipt.getStorageLocation(),
                userId
        );
    }

    public Receipt toEntity(ReceiptRequest request, Shop shop) {
        Receipt receipt = new Receipt();
        receipt.setShop(shop);
        receipt.setTotalPrice(request.totalPrice());
        receipt.setSerialNumber(request.serialNumber());
        return receipt;
    }
}
