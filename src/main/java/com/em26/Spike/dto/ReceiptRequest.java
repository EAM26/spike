package com.em26.Spike.dto;

import java.math.BigDecimal;

public record ReceiptRequest(
        BigDecimal totalPrice,
        Long serialNumber


) {
}
