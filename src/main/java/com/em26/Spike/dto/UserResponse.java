package com.em26.Spike.dto;

import com.em26.Spike.model.Receipt;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        List<ReceiptResponse> receipts
) {
}
