package com.lifesync.service;

import com.lifesync.entity.ExerciseRecord;
import com.lifesync.entity.User;
import com.lifesync.repository.ExerciseRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public List<ExerciseRecord> getUserExerciseHistory(Long userId) {
        return exerciseRepository.findByUserIdOrderByRecordDateDesc(userId);
    }

    @Transactional
    public ExerciseRecord logExercise(Long userId, ExerciseRecord record) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        record.setUser(user);
        if (record.getRecordDate() == null) {
            record.setRecordDate(LocalDate.now());
        }

        user.setXpPoints(user.getXpPoints() + 25);
        userRepository.save(user);

        return exerciseRepository.save(record);
    }
}
