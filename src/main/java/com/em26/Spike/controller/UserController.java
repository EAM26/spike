package com.em26.Spike.controller;

import com.em26.Spike.dto.UserRequest;
import com.em26.Spike.dto.UserResponse;
import com.em26.Spike.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String ping() {
        return "PING";
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok( userService.createUser(request));
//        return ResponseEntity.ok( userService.createUser(request));
    }
}
