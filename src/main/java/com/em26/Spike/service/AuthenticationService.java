package com.em26.Spike.service;

import com.em26.Spike.mapper.UserMapper;
import com.em26.Spike.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserMapper userMapper;

    public Long getCurrentUser(HttpServletRequest request) {
        Authentication authentication = (Authentication) request.getUserPrincipal();
        User currentUser = (User) authentication.getPrincipal();
        if(currentUser != null) return currentUser.getId();
        return null;
    }
}
