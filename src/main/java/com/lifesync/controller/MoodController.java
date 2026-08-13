package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.MoodRecord;
import com.lifesync.service.MoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mood")
@RequiredArgsConstructor
public class MoodController {

    private final MoodService moodService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<MoodRecord>>> getMoodHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<MoodRecord>>builder()
                .success(true)
                .data(moodService.getUserMoodHistory(userId))
                .build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<MoodRecord>> logMood(@PathVariable Long userId, @RequestBody MoodRecord record) {
        return ResponseEntity.ok(ApiResponse.<MoodRecord>builder()
                .success(true)
                .message("Mood logged successfully")
                .data(moodService.logMood(userId, record))
                .build());
    }
}
