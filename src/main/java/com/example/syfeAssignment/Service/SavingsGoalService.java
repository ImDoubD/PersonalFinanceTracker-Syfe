package com.example.syfeAssignment.Service;

import com.example.syfeAssignment.model.*;
import com.example.syfeAssignment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SavingsGoalService {
    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Autowired
    private UserRepository userRepository;

    public SavingsGoal addSavingsGoal(SavingsGoal savingsGoal) throws Exception {
        try {
            return savingsGoalRepository.save(savingsGoal);
        } catch (Exception e) {
            throw new Exception("Failed to add savings goal", e);
        }
    }

    public List<SavingsGoal> getSavingsGoalsByUser(Long userId) throws Exception {
        try {
            return savingsGoalRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new Exception("Failed to get savings goals for user: " + userId, e);
        }
    }

    public SavingsGoal updateSavingsGoal(SavingsGoal savingsGoal) throws Exception {
        try {
            return savingsGoalRepository.save(savingsGoal);
        } catch (Exception e) {
            throw new Exception("Failed to update savings goal", e);
        }
    }

    public void deleteSavingsGoal(Long id) throws Exception {
        try {
            savingsGoalRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Failed to delete savings goal with id: " + id, e);
        }
    }

    public List<SavingsGoal> getSavingsGoalProgress(Long userId) throws Exception {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new Exception("User not found with id: " + userId));
            Double totalAmount = user.getTotalAmount();
            List<SavingsGoal> savingsGoals = savingsGoalRepository.findByUserIdOrderByTargetDateAsc(userId);

            for (SavingsGoal goal : savingsGoals) {
                if (goal.getTargetAmount() <= totalAmount) {
                    goal.setGoalAchieved(true);
                    totalAmount -= goal.getTargetAmount();
                } else {
                    goal.setGoalAchieved(false);
                }
                goal.setPercentageRemaining((totalAmount / user.getTotalAmount()) * 100);
            }
            return savingsGoals;
        } catch (Exception e) {
            throw new Exception("Failed to get savings goal progress for user: " + userId, e);
        }
    }
}
