package com.lifesync.repository;

import com.lifesync.entity.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    List<HabitLog> findByHabitId(Long habitId);
    Optional<HabitLog> findByHabitIdAndLogDate(Long habitId, LocalDate logDate);
    List<HabitLog> findByHabitUserIdAndLogDate(Long userId, LocalDate logDate);
}
