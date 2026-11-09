package com.em26.Spike.dto;

import java.util.List;

public record ShopResponse(
        Long id,
        String name,
        List<ReceiptResponse> receipts
) {
}
