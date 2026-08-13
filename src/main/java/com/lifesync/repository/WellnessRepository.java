package com.lifesync.repository;

import com.lifesync.entity.WellnessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WellnessRepository extends JpaRepository<WellnessRecord, Long> {
    List<HabitLogRepository> findByUserId(Long userId);
    Optional<WellnessRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
    List<WellnessRecord> findByUserIdOrderByRecordDateDesc(Long userId);
}
