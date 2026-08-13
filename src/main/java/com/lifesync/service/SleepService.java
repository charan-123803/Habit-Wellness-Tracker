package com.lifesync.service;

import com.lifesync.entity.SleepRecord;
import com.lifesync.entity.User;
import com.lifesync.repository.SleepRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SleepService {

    private final SleepRepository sleepRepository;
    private final UserRepository userRepository;

    public List<SleepRecord> getUserSleepHistory(Long userId) {
        return sleepRepository.findByUserIdOrderByRecordDateDesc(userId);
    }

    @Transactional
    public SleepRecord logSleep(Long userId, SleepRecord record) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        record.setUser(user);
        if (record.getRecordDate() == null) {
            record.setRecordDate(LocalDate.now());
        }

        user.setXpPoints(user.getXpPoints() + 15);
        userRepository.save(user);

        return sleepRepository.save(record);
    }
}
