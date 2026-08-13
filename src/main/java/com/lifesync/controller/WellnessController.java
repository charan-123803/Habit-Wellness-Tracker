package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.WellnessRecord;
import com.lifesync.service.WellnessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wellness")
@RequiredArgsConstructor
public class WellnessController {

    private final WellnessService wellnessService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<WellnessRecord>>> getWellnessHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<WellnessRecord>>builder()
                .success(true)
                .data(wellnessService.getUserWellnessHistory(userId))
                .build());
    }

    @GetMapping("/user/{userId}/today")
    public ResponseEntity<ApiResponse<WellnessRecord>> getTodayWellness(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<WellnessRecord>builder()
                .success(true)
                .data(wellnessService.getTodayWellness(userId).orElse(null))
                .build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<WellnessRecord>> logWellness(@PathVariable Long userId, @RequestBody WellnessRecord record) {
        WellnessRecord saved = wellnessService.logWellness(userId, record);
        return ResponseEntity.ok(ApiResponse.<WellnessRecord>builder()
                .success(true)
                .message("Wellness record saved successfully")
                .data(saved)
                .build());
    }
}
