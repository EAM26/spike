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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@RequiredArgsConstructor
public class ReceiptService {


    private final ShopRepository shopRepository;
    private final ReceiptMapper receiptMapper;
    private final ReceiptRepository receiptRepository;

    public ReceiptResponse createReceipt(ReceiptRequest request, MultipartFile document, Long shopId) {
        Shop shop = getShop(shopId);
        Receipt receipt = receiptMapper.toEntity(request, shop);
        String storageLocation = storeDocument(document, shop.getName(), receipt.getSerialNumber());
        receipt.setStorageLocation(storageLocation);

        return receiptMapper.toResponse(receiptRepository.save(receipt));
    }

    private String storeDocument(MultipartFile document, String shopName, Long serialNumber) {
        File file = new File("C:\\spike-data\\storage\\" + shopName+ "-" + serialNumber + ".pdf");
        try {
            document.transferTo(file);
            return file.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error in storing document: " + e.getMessage());
        }
    }

    private Shop getShop(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() ->
                new RuntimeException("Shop not found: " + shopId));
    }
}
