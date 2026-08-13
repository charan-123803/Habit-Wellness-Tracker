package com.lifesync.repository;

import com.lifesync.entity.MoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoodRepository extends JpaRepository<MoodRecord, Long> {
    List<MoodRecord> findByUserIdOrderByRecordDateDesc(Long userId);
    Optional<MoodRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
}
