package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.User;
import com.lifesync.repository.BadgeRepository;
import com.lifesync.repository.UserBadgeRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gamification")
@RequiredArgsConstructor
public class GamificationController {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;

    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getGamificationStats(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> stats = new HashMap<>();
        stats.put("xpPoints", user.getXpPoints());
        stats.put("userLevel", user.getUserLevel());
        stats.put("coins", user.getCoins());
        stats.put("badgesCount", userBadgeRepository.findByUserId(userId).size());

        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true)
                .data(stats)
                .build());
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<ApiResponse<List<User>>> getLeaderboard() {
        List<User> topUsers = userRepository.findAll().stream()
                .sorted((u1, u2) -> u2.getXpPoints().compareTo(u1.getXpPoints()))
                .limit(10)
                .toList();

        return ResponseEntity.ok(ApiResponse.<List<User>>builder()
                .success(true)
                .data(topUsers)
                .build());
    }
}
