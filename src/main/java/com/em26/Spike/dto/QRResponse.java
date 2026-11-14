package com.em26.Spike.dto;

public record QRResponse(
        byte[] image,
        String url
) {
}
