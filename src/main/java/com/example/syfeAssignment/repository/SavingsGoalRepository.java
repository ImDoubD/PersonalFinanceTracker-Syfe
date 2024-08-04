package com.example.syfeAssignment.repository;

import com.example.syfeAssignment.model.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findByUserId(Long userId);
    List<SavingsGoal> findByUserIdOrderByTargetDateAsc(Long userId);
}
