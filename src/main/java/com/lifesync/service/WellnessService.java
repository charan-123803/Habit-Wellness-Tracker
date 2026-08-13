package com.lifesync.service;

import com.lifesync.entity.User;
import com.lifesync.entity.WellnessRecord;
import com.lifesync.repository.UserRepository;
import com.lifesync.repository.WellnessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WellnessService {

    private final WellnessRepository wellnessRepository;
    private final UserRepository userRepository;

    public List<WellnessRecord> getUserWellnessHistory(Long userId) {
        return wellnessRepository.findByUserIdOrderByRecordDateDesc(userId);
    }

    public Optional<WellnessRecord> getTodayWellness(Long userId) {
        return wellnessRepository.findByUserIdAndRecordDate(userId, LocalDate.now());
    }

    @Transactional
    public WellnessRecord logWellness(Long userId, WellnessRecord record) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        record.setUser(user);
        if (record.getRecordDate() == null) {
            record.setRecordDate(LocalDate.now());
        }

        Optional<WellnessRecord> existing = wellnessRepository.findByUserIdAndRecordDate(userId, record.getRecordDate());
        if (existing.isPresent()) {
            WellnessRecord current = existing.get();
            if (record.getHeightCm() != null) current.setHeightCm(record.getHeightCm());
            if (record.getWeightKg() != null) current.setWeightKg(record.getWeightKg());
            if (record.getSystolicBp() != null) current.setSystolicBp(record.getSystolicBp());
            if (record.getDiastolicBp() != null) current.setDiastolicBp(record.getDiastolicBp());
            if (record.getHeartRate() != null) current.setHeartRate(record.getHeartRate());
            if (record.getBloodSugar() != null) current.setBloodSugar(record.getBloodSugar());
            if (record.getOxygenLevel() != null) current.setOxygenLevel(record.getOxygenLevel());
            if (record.getWaterIntakeMl() != null) current.setWaterIntakeMl(current.getWaterIntakeMl() + record.getWaterIntakeMl());
            if (record.getSteps() != null) current.setSteps(record.getSteps());
            if (record.getCaloriesBurned() != null) current.setCaloriesBurned(record.getCaloriesBurned());
            return wellnessRepository.save(current);
        }

        return wellnessRepository.save(record);
    }
}
