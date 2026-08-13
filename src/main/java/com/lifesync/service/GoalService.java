package com.lifesync.service;

import com.lifesync.entity.Goal;
import com.lifesync.entity.User;
import com.lifesync.repository.GoalRepository;
import com.lifesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public List<Goal> getUserGoals(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    @Transactional
    public Goal createGoal(Long userId, Goal goal) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    @Transactional
    public Goal updateGoalProgress(Long goalId, Double currentValue) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal not found"));
        goal.setCurrentValue(currentValue);
        if (currentValue >= goal.getTargetValue()) {
            goal.setStatus("COMPLETED");
            User user = goal.getUser();
            user.setXpPoints(user.getXpPoints() + 100);
            userRepository.save(user);
        }
        return goalRepository.save(goal);
    }

    @Transactional
    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }
}
