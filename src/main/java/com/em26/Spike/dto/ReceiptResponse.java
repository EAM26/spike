package com.em26.Spike.dto;

import java.math.BigDecimal;

public record ReceiptResponse(
        Long id,
        Long serialNumber,
        BigDecimal totalAmount,
        String shopName,
        String storageLocation
) {
}
