package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.dto.AuthDTOs;
import com.lifesync.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthDTOs.JwtResponse>> login(@Valid @RequestBody AuthDTOs.LoginRequest request) {
        ApiResponse<AuthDTOs.JwtResponse> response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody AuthDTOs.RegisterRequest request) {
        ApiResponse<String> response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody AuthDTOs.PasswordResetRequest request) {
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("Password reset instructions sent to " + request.getEmail())
                .data("Email Sent")
                .build());
    }
}
