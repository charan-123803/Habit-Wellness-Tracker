package com.lifesync.repository;

import com.lifesync.entity.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseRecord, Long> {
    List<ExerciseRecord> findByUserIdOrderByRecordDateDesc(Long userId);
    List<ExerciseRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
}
