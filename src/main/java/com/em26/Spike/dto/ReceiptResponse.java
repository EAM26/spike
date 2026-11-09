package com.em26.Spike.dto;

import java.math.BigDecimal;

public record ReceiptResponse(
        Long id,
        BigDecimal totalAmount
) {
}
