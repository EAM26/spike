package com.em26.Spike.service;

import com.em26.Spike.dto.UserRequest;
import com.em26.Spike.dto.UserResponse;
import com.em26.Spike.mapper.UserMapper;
import com.em26.Spike.model.User;
import com.em26.Spike.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest request) {
        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse findById(Long id) {
        User user =  userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found: " + id));
        return userMapper.toResponse(user);
    }
}
