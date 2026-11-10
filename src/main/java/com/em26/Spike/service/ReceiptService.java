package com.em26.Spike.service;

import com.em26.Spike.dto.ReceiptRequest;
import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.mapper.ReceiptMapper;
import com.em26.Spike.model.Receipt;
import com.em26.Spike.model.Shop;
import com.em26.Spike.model.User;
import com.em26.Spike.repository.ReceiptRepository;
import com.em26.Spike.repository.ShopRepository;
import com.em26.Spike.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;

@Service
@RequiredArgsConstructor
public class ReceiptService {


    private final ShopRepository shopRepository;
    private final ReceiptMapper receiptMapper;
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final UrlBuilder urlBuilder;

//    todo: this wil be the start of creating  claim with qrcode endpoint and login flow
//    public String createReceipt(ReceiptRequest request, MultipartFile document, Long shopId) {
//        Shop shop = getShop(shopId);
//        Receipt receipt = receiptMapper.toEntity(request, shop);
//        String storageLocation = storeDocument(document, shop.getName(), receipt.getSerialNumber());
//        receipt.setStorageLocation(storageLocation);
//        receiptRepository.save(receipt);
//        return urlBuilder.createClaimUrl(receipt.getId());
//    }

    public String createReceipt(ReceiptRequest request, MultipartFile document, Long shopId) {
        Shop shop = getShop(shopId);
        Receipt receipt = receiptMapper.toEntity(request, shop);
        String storageLocation = storeDocument(document, shop.getName(), receipt.getSerialNumber());
        receipt.setStorageLocation(storageLocation);
        receiptRepository.save(receipt);

//        Shortcut to claim with fake userId
        claimReceipt(1L, receipt.getId());
        return "receipt claimed";
    }

    private String storeDocument(MultipartFile document, String shopName, Long serialNumber) {
        File file = new File("C:\\spike-data\\storage\\" + shopName + "-" + serialNumber + ".pdf");
        try {
            document.transferTo(file);
            return file.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Error in storing document: " + e.getMessage());
        }
    }

    private Shop getShop(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found: " + shopId));
    }

    public ReceiptResponse claimReceipt(Long userId, Long receiptId) {
        User user = getUser(userId);
        Receipt receipt = getReceipt(receiptId);
        if (receipt.getUser() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Receipt is already claimed");
        }
        receipt.setUser(user);
        return receiptMapper.toResponse(receiptRepository.save(receipt));

    }

    private Receipt getReceipt(Long receiptId) {
        return receiptRepository.findById(receiptId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt not found: " + receiptId));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));
    }
}
