package com.em26.Spike.service;

import com.em26.Spike.dto.ReceiptRequest;
import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.mapper.ReceiptMapper;
import com.em26.Spike.model.Receipt;
import com.em26.Spike.model.Shop;
import com.em26.Spike.repository.ReceiptRepository;
import com.em26.Spike.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptService {


    private final ShopRepository shopRepository;
    private final ReceiptMapper receiptMapper;
    private final ReceiptRepository receiptRepository;

    public ReceiptResponse createReceipt(ReceiptRequest request, Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() ->
                new RuntimeException("Shop not found: " + shopId));
        Receipt receipt = receiptRepository.save(receiptMapper.toEntity(request, shop));

        return receiptMapper.toResponse(receipt);
    }
}
