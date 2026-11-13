package com.em26.Spike.mapper;

import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.dto.UserRequest;
import com.em26.Spike.dto.UserResponse;
import com.em26.Spike.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ReceiptMapper receiptMapper;

    public User toEntity(UserRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setReceipts(new ArrayList<>());
        return user;
    }

    public UserResponse toResponse(User user) {
        List<ReceiptResponse> receipts = new ArrayList<>();
        if(!user.getReceipts().isEmpty())
            receipts = user.getReceipts().stream()
                    .map(receiptMapper::toResponse)
                    .toList();
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                receipts

        );
    }
}
