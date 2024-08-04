package com.example.syfeAssignment.controller;

import com.example.syfeAssignment.model.*;
import com.example.syfeAssignment.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {
    @Autowired
    private SavingsGoalService savingsGoalService;

    @PostMapping
    public ResponseEntity<?> addSavingsGoal(@RequestBody SavingsGoal savingsGoal) {
        try {
            SavingsGoal createdSavingsGoal = savingsGoalService.addSavingsGoal(savingsGoal);
            return ResponseEntity.ok(createdSavingsGoal);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add savings goal: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getSavingsGoalsByUser(@PathVariable Long userId) {
        try {
            List<SavingsGoal> savingsGoals = savingsGoalService.getSavingsGoalsByUser(userId);
            return ResponseEntity.ok(savingsGoals);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch savings goals: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSavingsGoal(@PathVariable Long id, @RequestBody SavingsGoal savingsGoal) {
        try {
            savingsGoal.setId(id);
            SavingsGoal updatedSavingsGoal = savingsGoalService.updateSavingsGoal(savingsGoal);
            return ResponseEntity.ok(updatedSavingsGoal);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update savings goal: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSavingsGoal(@PathVariable Long id) {
        try {
            savingsGoalService.deleteSavingsGoal(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete savings goal: " + e.getMessage());
        }
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<List<SavingsGoal>> getSavingsGoalProgress(@PathVariable Long userId) {
        try {
            List<SavingsGoal> savingsGoals = savingsGoalService.getSavingsGoalProgress(userId);
            return new ResponseEntity<>(savingsGoals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}