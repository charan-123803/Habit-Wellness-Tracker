package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.User;
import com.lifesync.repository.HabitRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAdminStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalHabits", habitRepository.count());
        stats.put("systemHealth", "100% Operational");
        stats.put("activeSubscriptions", "Enterprise");

        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true)
                .data(stats)
                .build());
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.<List<User>>builder()
                .success(true)
                .data(userRepository.findAll())
                .build());
    }

    @PutMapping("/users/{userId}/toggle-enable")
    public ResponseEntity<ApiResponse<User>> toggleUserEnable(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(!user.getEnabled());
        return ResponseEntity.ok(ApiResponse.<User>builder()
                .success(true)
                .message("User status updated")
                .data(userRepository.save(user))
                .build());
    }
}
