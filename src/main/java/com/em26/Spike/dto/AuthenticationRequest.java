package com.em26.Spike.dto;

public record AuthenticationRequest(
        String username,
        String password
) {
}
