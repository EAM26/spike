package com.em26.Spike.dto;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        String role,
        List<ReceiptResponse> receipts
) {
}
