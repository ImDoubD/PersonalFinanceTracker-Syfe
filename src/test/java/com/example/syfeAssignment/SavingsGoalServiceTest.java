package com.example.syfeAssignment;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.syfeAssignment.model.SavingsGoal;
import com.example.syfeAssignment.model.User;
import com.example.syfeAssignment.repository.SavingsGoalRepository;
import com.example.syfeAssignment.repository.UserRepository;
import com.example.syfeAssignment.Service.SavingsGoalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SavingsGoalServiceTest {

    @Mock
    private SavingsGoalRepository savingsGoalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SavingsGoalService savingsGoalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddSavingsGoal() throws Exception {
        SavingsGoal goal = new SavingsGoal();
        goal.setTargetAmount(500.0);
        goal.setTargetDate(LocalDate.now().plusMonths(6));

        when(savingsGoalRepository.save(any(SavingsGoal.class))).thenReturn(goal);

        SavingsGoal savedGoal = savingsGoalService.addSavingsGoal(goal);
        assertNotNull(savedGoal);
    }

    @Test
    public void testGetSavingsGoalsByUser() throws Exception {
        User user = new User();
        user.setId(1L);

        SavingsGoal goal = new SavingsGoal();
        goal.setUser(user);

        when(savingsGoalRepository.findByUserId(1L)).thenReturn(Collections.singletonList(goal));

        List<SavingsGoal> goals = savingsGoalService.getSavingsGoalsByUser(1L);
        assertNotNull(goals);
        assertEquals(1, goals.size());
    }

    @Test
    public void testUpdateSavingsGoal() throws Exception {
        SavingsGoal goal = new SavingsGoal();
        goal.setId(1L);
        goal.setTargetAmount(1000.0);

        when(savingsGoalRepository.save(any(SavingsGoal.class))).thenReturn(goal);

        SavingsGoal updatedGoal = savingsGoalService.updateSavingsGoal(goal);
        assertNotNull(updatedGoal);
        assertEquals(1000.0, updatedGoal.getTargetAmount());
    }

    @Test
    public void testDeleteSavingsGoal() throws Exception {
        doNothing().when(savingsGoalRepository).deleteById(1L);
        savingsGoalService.deleteSavingsGoal(1L);
        verify(savingsGoalRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetSavingsGoalProgress() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setTotalAmount(1000.0);

        SavingsGoal goal = new SavingsGoal();
        goal.setUser(user);
        goal.setTargetAmount(500.0);
        goal.setTargetDate(LocalDate.now().plusMonths(6));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(savingsGoalRepository.findByUserIdOrderByTargetDateAsc(1L)).thenReturn(Collections.singletonList(goal));

        List<SavingsGoal> goals = savingsGoalService.getSavingsGoalProgress(1L);
        assertNotNull(goals);
        assertEquals(1, goals.size());
        assertTrue(goals.get(0).getGoalAchieved());
        assertEquals(50.0, goals.get(0).getPercentageRemaining());
    }
}
