package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.MealRecord;
import com.lifesync.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<MealRecord>>> getMealHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<MealRecord>>builder()
                .success(true)
                .data(mealService.getUserMealHistory(userId))
                .build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<MealRecord>> logMeal(@PathVariable Long userId, @RequestBody MealRecord record) {
        return ResponseEntity.ok(ApiResponse.<MealRecord>builder()
                .success(true)
                .message("Meal log saved successfully")
                .data(mealService.logMeal(userId, record))
                .build());
    }
}
