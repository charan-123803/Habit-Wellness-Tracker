package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.Goal;
import com.lifesync.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Goal>>> getUserGoals(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<Goal>>builder()
                .success(true)
                .data(goalService.getUserGoals(userId))
                .build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Goal>> createGoal(@PathVariable Long userId, @RequestBody Goal goal) {
        return ResponseEntity.ok(ApiResponse.<Goal>builder()
                .success(true)
                .message("Goal created")
                .data(goalService.createGoal(userId, goal))
                .build());
    }

    @PutMapping("/{goalId}/progress")
    public ResponseEntity<ApiResponse<Goal>> updateProgress(@PathVariable Long goalId, @RequestParam Double currentValue) {
        return ResponseEntity.ok(ApiResponse.<Goal>builder()
                .success(true)
                .message("Goal progress updated")
                .data(goalService.updateGoalProgress(goalId, currentValue))
                .build());
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<ApiResponse<Void>> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Goal deleted")
                .build());
    }
}
