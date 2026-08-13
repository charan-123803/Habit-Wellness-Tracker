package com.lifesync.service;

import com.lifesync.entity.MealRecord;
import com.lifesync.entity.User;
import com.lifesync.repository.MealRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public List<MealRecord> getUserMealHistory(Long userId) {
        return mealRepository.findByUserIdOrderByRecordDateDesc(userId);
    }

    @Transactional
    public MealRecord logMeal(Long userId, MealRecord record) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        record.setUser(user);
        if (record.getRecordDate() == null) {
            record.setRecordDate(LocalDate.now());
        }

        return mealRepository.save(record);
    }
}
