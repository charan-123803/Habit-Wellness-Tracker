package com.lifesync.repository;

import com.lifesync.entity.MealRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<MealRecord, Long> {
    List<MealRecord> findByUserIdOrderByRecordDateDesc(Long userId);
    List<MealRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
}
