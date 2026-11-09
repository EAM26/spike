package com.em26.Spike.mapper;

import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.model.Receipt;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public ReceiptResponse toResponse(Receipt receipt) {
        return new ReceiptResponse(
                receipt.getId(),
                receipt.getTotalAmount()
        );
    }
}
