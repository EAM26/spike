package com.em26.Spike.controller;

import com.em26.Spike.dto.ReceiptResponse;
import com.em26.Spike.dto.UserRequest;
import com.em26.Spike.dto.UserResponse;
import com.em26.Spike.service.AuthenticationService;
import com.em26.Spike.service.ReceiptService;
import com.em26.Spike.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReceiptService receiptService;
    private final AuthenticationService authenticationService;

    @GetMapping("/ping")
    public String ping() {
        return "PING";
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    @PostMapping("/{userId}/claim/{receiptId}")
    public ResponseEntity<ReceiptResponse> claimReceipt(@PathVariable Long userId, @PathVariable Long receiptId) {
        return ResponseEntity.ok(receiptService.claimReceipt(userId, receiptId));
    }

    @GetMapping(path = "/i-want-your-user-id/claim/{receiptId}")
//    @GetMapping(path = "/i-want-your-user-id/claim/{receiptId}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<ReceiptResponse> showClaimForm(@PathVariable Long receiptId, HttpServletRequest request) {
        Long userId = authenticationService.getCurrentUser(request);
//        String html = """
//                  <form method="post" action="/api/users/%d/claim/%d">
//                  <button type="submit">Claim bon</button>
//                </form>
//                """.formatted(userId, receiptId);
//        receiptService.claimReceipt(userId, receiptId);
        return ResponseEntity.ok().body(receiptService.claimReceipt(userId, receiptId));
//        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }
}
