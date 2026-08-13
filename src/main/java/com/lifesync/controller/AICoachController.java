package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/aicoach")
@RequiredArgsConstructor
public class AICoachController {

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getRecommendations(@PathVariable Long userId) {
        List<Map<String, String>> recommendations = List.of(
                Map.of("category", "Exercise", "title", "Workout Tip", "message", "You skipped exercise yesterday. Try a brisk 20-minute morning walk today!"),
                Map.of("category", "Water", "title", "Hydration Goal", "message", "Drink one more glass of water before 4 PM to stay energized."),
                Map.of("category", "Sleep", "title", "Rest & Recovery", "message", "Aim to sleep by 10:30 PM tonight to optimize deep REM sleep cycle."),
                Map.of("category", "Streak", "title", "Habit Consistency", "message", "Keep your 5-day Morning Hydration streak alive!")
        );

        return ResponseEntity.ok(ApiResponse.<List<Map<String, String>>>builder()
                .success(true)
                .data(recommendations)
                .build());
    }
}
