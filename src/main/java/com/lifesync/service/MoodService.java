package com.lifesync.service;

import com.lifesync.entity.MoodRecord;
import com.lifesync.entity.User;
import com.lifesync.repository.MoodRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoodService {

    private final MoodRepository moodRepository;
    private final UserRepository userRepository;

    public List<MoodRecord> getUserMoodHistory(Long userId) {
        return moodRepository.findByUserIdOrderByRecordDateDesc(userId);
    }

    @Transactional
    public MoodRecord logMood(Long userId, MoodRecord record) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        record.setUser(user);
        if (record.getRecordDate() == null) {
            record.setRecordDate(LocalDate.now());
        }

        return moodRepository.save(record);
    }
}
