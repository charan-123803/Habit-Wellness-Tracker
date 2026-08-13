package com.lifesync.repository;

import com.lifesync.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUserIdAndIsArchivedFalse(Long userId);
    List<Habit> findByUserId(Long userId);
    List<Habit> findByUserIdAndCategory(Long userId, String category);
}
