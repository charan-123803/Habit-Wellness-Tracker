package com.lifesync.repository;

import com.lifesync.entity.SleepRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SleepRepository extends JpaRepository<SleepRecord, Long> {
    List<SleepRecord> findByUserIdOrderByRecordDateDesc(Long userId);
    Optional<SleepRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
}
