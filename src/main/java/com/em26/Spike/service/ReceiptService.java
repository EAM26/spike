package com.em26.Spike.service;

import com.em26.Spike.dto.QRResponse;
import com.em26.Spike.dto.ReceiptRequest;
import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.mapper.ReceiptMapper;
import com.em26.Spike.model.Receipt;
import com.em26.Spike.model.Shop;
import com.em26.Spike.model.User;
import com.em26.Spike.repository.ReceiptRepository;
import com.em26.Spike.repository.ShopRepository;
import com.em26.Spike.repository.UserRepository;
import com.em26.Spike.util.QRUtil;
import com.em26.Spike.util.UrlBuilder;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReceiptService {


    private final ShopRepository shopRepository;
    private final ReceiptMapper receiptMapper;
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final UrlBuilder urlBuilder;
    private final QRUtil qrUtil;

    public QRResponse createReceipt(ReceiptRequest request, MultipartFile document, Long shopId) throws IOException, WriterException {
        Shop shop = getShop(shopId);
        Receipt receipt = receiptMapper.toEntity(request, shop);
        String storageLocation = storeDocument(document, shop.getName(), receipt.getSerialNumber());
        receipt.setStorageLocation(storageLocation);
        receiptRepository.save(receipt);

        String uriClaim =  urlBuilder.createClaimUrlForQR(receipt.getId());
        byte[] qrImage = qrUtil.generateQRCode(uriClaim, 300, 300);
        return new QRResponse(qrImage, uriClaim);
    }

//    public String createReceipt(ReceiptRequest request, MultipartFile document, Long shopId) {
//        Shop shop = getShop(shopId);
//        Receipt receipt = receiptMapper.toEntity(request, shop);
//        String storageLocation = storeDocument(document, shop.getName(), receipt.getSerialNumber());
//        receipt.setStorageLocation(storageLocation);
//        receiptRepository.save(receipt);
//
////        Shortcut to claim with fake userId
//        claimReceipt(1L, receipt.getId());
//        return "receipt claimed";
//    }

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

    public ReceiptResponse claimReceipt(Authentication authentication, Long receiptId) {
        User currentUser  = (User) authentication.getPrincipal();
        Receipt receipt = getReceipt(receiptId);
        if (receipt.getUser() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Receipt is already claimed");
        }
        receipt.setUser(currentUser);
        return receiptMapper.toResponse(receiptRepository.save(receipt));
    }

    private Receipt getReceipt(Long receiptId) {
        return receiptRepository.findById(receiptId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt not found: " + receiptId));
    }


}
