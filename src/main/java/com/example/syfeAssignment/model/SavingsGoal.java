package com.example.syfeAssignment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "savingsGoal")
public class SavingsGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double targetAmount;
    private LocalDate targetDate;
    @ManyToOne
    private User user;

    @Transient
    private Boolean goalAchieved;
    @Transient
    private Double percentageRemaining;

    public Boolean getGoalAchieved() {
        return goalAchieved;
    }

    public void setGoalAchieved(Boolean goalAchieved) {
        this.goalAchieved = goalAchieved;
    }

    public Double getPercentageRemaining() {
        return percentageRemaining;
    }

    public void setPercentageRemaining(Double percentageRemaining) {
        this.percentageRemaining = percentageRemaining;
    }
}