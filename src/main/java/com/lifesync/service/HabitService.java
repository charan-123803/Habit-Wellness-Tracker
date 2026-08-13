package com.lifesync.service;

import com.lifesync.entity.Habit;
import com.lifesync.entity.HabitLog;
import com.lifesync.entity.User;
import com.lifesync.repository.HabitLogRepository;
import com.lifesync.repository.HabitRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final UserRepository userRepository;

    public List<Habit> getHabitsForUser(Long userId) {
        return habitRepository.findByUserIdAndIsArchivedFalse(userId);
    }

    @Transactional
    public Habit createHabit(Long userId, Habit habitData) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        habitData.setUser(user);
        return habitRepository.save(habitData);
    }

    @Transactional
    public Habit updateHabit(Long habitId, Habit habitData) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setTitle(habitData.getTitle());
        habit.setDescription(habitData.getDescription());
        habit.setCategory(habitData.getCategory());
        habit.setPriority(habitData.getPriority());
        habit.setFrequency(habitData.getFrequency());
        habit.setTargetCount(habitData.getTargetCount());
        return habitRepository.save(habit);
    }

    @Transactional
    public void archiveHabit(Long habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setIsArchived(true);
        habitRepository.save(habit);
    }

    @Transactional
    public void deleteHabit(Long habitId) {
        habitRepository.deleteById(habitId);
    }

    @Transactional
    public HabitLog logHabitStatus(Long habitId, LocalDate date, String status, String notes) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new RuntimeException("Habit not found"));
        Optional<HabitLog> existingLog = habitLogRepository.findByHabitIdAndLogDate(habitId, date);

        HabitLog log;
        if (existingLog.isPresent()) {
            log = existingLog.get();
            log.setStatus(status);
            log.setNotes(notes);
        } else {
            log = HabitLog.builder()
                    .habit(habit)
                    .logDate(date)
                    .status(status)
                    .notes(notes)
                    .build();
        }

        if ("COMPLETED".equalsIgnoreCase(status)) {
            habit.setCurrentStreak(habit.getCurrentStreak() + 1);
            if (habit.getCurrentStreak() > habit.getBestStreak()) {
                habit.setBestStreak(habit.getCurrentStreak());
            }
            // Award XP to user
            User user = habit.getUser();
            user.setXpPoints(user.getXpPoints() + 20);
            userRepository.save(user);
        }

        habitRepository.save(habit);
        return habitLogRepository.save(log);
    }
}
