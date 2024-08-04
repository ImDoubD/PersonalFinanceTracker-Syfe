package com.example.syfeAssignment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.syfeAssignment.Service.TransactionService;
import com.example.syfeAssignment.model.Transaction;
import com.example.syfeAssignment.model.User;
import com.example.syfeAssignment.repository.TransactionRepository;
import com.example.syfeAssignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTransaction() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setTotalAmount(1000.0);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(200.0);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction savedTransaction = transactionService.addTransaction(transaction);
        assertNotNull(savedTransaction);
        assertEquals(800.0, user.getTotalAmount());
    }

    @Test
    public void testAddTransactionInsufficientFunds() {
        User user = new User();
        user.setId(1L);
        user.setTotalAmount(100.0);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(200.0);

        Exception exception = assertThrows(Exception.class, () -> {
            transactionService.addTransaction(transaction);
        });

        assertTrue(exception.getMessage().contains("insufficient funds"));
    }

    @Test
    public void testGetTransactionsByUser() throws Exception {
        User user = new User();
        user.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setUser(user);

        when(transactionRepository.findByUserId(1L)).thenReturn(Collections.singletonList(transaction));

        List<Transaction> transactions = transactionService.getTransactionsByUser(1L);
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setTotalAmount(1000.0);

        Transaction existingTransaction = new Transaction();
        existingTransaction.setId(1L);
        existingTransaction.setUser(user);
        existingTransaction.setAmount(200.0);

        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setId(1L);
        updatedTransaction.setUser(user);
        updatedTransaction.setAmount(300.0);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);
        when(userRepository.save(any(User.class))).thenReturn(user);

        Transaction result = transactionService.updateTransaction(updatedTransaction);

        assertNotNull(result);
        assertEquals(900.0, user.getTotalAmount(), 0.001);
        verify(transactionRepository).save(updatedTransaction);
        verify(userRepository).save(user);
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        doNothing().when(transactionRepository).deleteById(1L);
        transactionService.deleteTransaction(1L);
        verify(transactionRepository, times(1)).deleteById(1L);
    }
}
