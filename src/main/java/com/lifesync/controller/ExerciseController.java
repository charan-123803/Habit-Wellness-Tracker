package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.ExerciseRecord;
import com.lifesync.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ExerciseRecord>>> getExerciseHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<ExerciseRecord>>builder()
                .success(true)
                .data(exerciseService.getUserExerciseHistory(userId))
                .build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<ExerciseRecord>> logExercise(@PathVariable Long userId, @RequestBody ExerciseRecord record) {
        return ResponseEntity.ok(ApiResponse.<ExerciseRecord>builder()
                .success(true)
                .message("Exercise log saved successfully")
                .data(exerciseService.logExercise(userId, record))
                .build());
    }
}
