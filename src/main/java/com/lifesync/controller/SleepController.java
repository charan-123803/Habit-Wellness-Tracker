package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.SleepRecord;
import com.lifesync.service.SleepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sleep")
@RequiredArgsConstructor
public class SleepController {

    private final SleepService sleepService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<SleepRecord>>> getSleepHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<SleepRecord>>builder()
                .success(true)
                .data(sleepService.getUserSleepHistory(userId))
                .build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<SleepRecord>> logSleep(@PathVariable Long userId, @RequestBody SleepRecord record) {
        return ResponseEntity.ok(ApiResponse.<SleepRecord>builder()
                .success(true)
                .message("Sleep log saved successfully")
                .data(sleepService.logSleep(userId, record))
                .build());
    }
}
