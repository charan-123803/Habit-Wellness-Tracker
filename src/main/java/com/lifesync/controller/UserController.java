package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.User;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserProfile(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(ApiResponse.<User>builder().success(true).data(user).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateProfile(@PathVariable Long id, @RequestBody User profileData) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (profileData.getFullName() != null) user.setFullName(profileData.getFullName());
        if (profileData.getAvatarUrl() != null) user.setAvatarUrl(profileData.getAvatarUrl());
        if (profileData.getHeightCm() != null) user.setHeightCm(profileData.getHeightCm());
        if (profileData.getWeightKg() != null) user.setWeightKg(profileData.getWeightKg());
        if (profileData.getGender() != null) user.setGender(profileData.getGender());
        if (profileData.getAge() != null) user.setAge(profileData.getAge());
        if (profileData.getLanguage() != null) user.setLanguage(profileData.getLanguage());
        if (profileData.getTimeZone() != null) user.setTimeZone(profileData.getTimeZone());

        User updated = userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.<User>builder().success(true).message("Profile updated!").data(updated).build());
    }
}
