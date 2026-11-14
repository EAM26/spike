package com.em26.Spike.controller;

import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.dto.UserRequest;
import com.em26.Spike.dto.UserResponse;
import com.em26.Spike.service.AuthenticationService;
import com.em26.Spike.service.ReceiptService;
import com.em26.Spike.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReceiptService receiptService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    @PostMapping("/claim-receipt/{receiptId}")
    public ResponseEntity<ReceiptResponse> claimReceipt(Authentication authentication, @PathVariable Long receiptId) {
        return ResponseEntity.ok(receiptService.claimReceipt(authentication, receiptId));
    }

    @GetMapping(path = "/claim-receipt/{receiptId}", produces = MediaType.TEXT_HTML_VALUE)
    public String showClaimForm() {
        return  """
                  <form method="post">
                  <button type="submit">Claim bon</button>
                </form>
                """;

    }
}
