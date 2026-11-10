package com.em26.Spike.service;

import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {


    public String createClaimUrl(Long receiptId) {
        return "http://localhost:8080/api/users/i-want-your-user-id/claim/" + receiptId;
    }
}
