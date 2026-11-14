package com.em26.Spike.controller;

import com.em26.Spike.dto.QRResponse;
import com.em26.Spike.dto.ReceiptRequest;
import com.em26.Spike.dto.ShopRequest;
import com.em26.Spike.dto.ShopResponse;
import com.em26.Spike.service.ReceiptService;
import com.em26.Spike.service.ShopService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<ShopResponse> createShop(@RequestBody ShopRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shopService.createShop(request));
    }

    @PostMapping(path = "{shopId}/receipt", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> createReceipt(@PathVariable Long shopId, @RequestPart ReceiptRequest request,
                                                @RequestPart MultipartFile document) throws IOException, WriterException {
        QRResponse qrResponse = receiptService.createReceipt(request, document, shopId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(receiptService.createReceipt(request, document, shopId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.IMAGE_PNG)
                .header("X-Claim-Url", qrResponse.url())
                .body(qrResponse.image());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopResponse> getShop(@PathVariable Long id) {
        return ResponseEntity.ok(shopService.findById(id));
    }
}
