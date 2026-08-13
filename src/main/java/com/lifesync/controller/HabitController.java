package com.lifesync.controller;

import com.lifesync.dto.ApiResponse;
import com.lifesync.entity.Habit;
import com.lifesync.entity.HabitLog;
import com.lifesync.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Habit>>> getUserHabits(@PathVariable Long userId) {
        List<Habit> habits = habitService.getHabitsForUser(userId);
        return ResponseEntity.ok(ApiResponse.<List<Habit>>builder().success(true).data(habits).build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Habit>> createHabit(@PathVariable Long userId, @RequestBody Habit habit) {
        Habit created = habitService.createHabit(userId, habit);
        return ResponseEntity.ok(ApiResponse.<Habit>builder().success(true).message("Habit created!").data(created).build());
    }

    @PutMapping("/{habitId}")
    public ResponseEntity<ApiResponse<Habit>> updateHabit(@PathVariable Long habitId, @RequestBody Habit habit) {
        Habit updated = habitService.updateHabit(habitId, habit);
        return ResponseEntity.ok(ApiResponse.<Habit>builder().success(true).message("Habit updated!").data(updated).build());
    }

    @PostMapping("/{habitId}/log")
    public ResponseEntity<ApiResponse<HabitLog>> logHabit(
            @PathVariable Long habitId,
            @RequestParam(required = false) String date,
            @RequestParam String status,
            @RequestParam(required = false) String notes) {
        LocalDate logDate = date != null ? LocalDate.parse(date) : LocalDate.now();
        HabitLog log = habitService.logHabitStatus(habitId, logDate, status, notes);
        return ResponseEntity.ok(ApiResponse.<HabitLog>builder().success(true).message("Habit status updated!").data(log).build());
    }

    @PutMapping("/{habitId}/archive")
    public ResponseEntity<ApiResponse<Void>> archiveHabit(@PathVariable Long habitId) {
        habitService.archiveHabit(habitId);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Habit archived").build());
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<ApiResponse<Void>> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Habit deleted").build());
    }
}
