package com.example.syfeAssignment.Service;

import com.example.syfeAssignment.model.*;
import com.example.syfeAssignment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    public Transaction addTransaction(Transaction transaction) throws Exception {
        try {
            User user = transaction.getUser();
            double newTotalAmount = user.getTotalAmount() - transaction.getAmount();

            if (newTotalAmount < 0) {
                throw new Exception("Transaction not successful: insufficient funds.");
            }

            // Save the transaction first
            Transaction savedTransaction = transactionRepository.save(transaction);

            // Update the user's total amount
            user.setTotalAmount(newTotalAmount);
            userRepository.save(user);

            return savedTransaction;
        } catch (Exception e) {
            throw new Exception("Error adding transaction: " + e.getMessage());
        }
    }

    public List<Transaction> getTransactionsByUser(Long userId) throws Exception {
        try {
            return transactionRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new Exception("Error fetching transactions for user: " + e.getMessage());
        }
    }

    public Transaction updateTransaction(Transaction transaction) throws Exception {
        try {
            // Find the existing transaction
            Transaction existingTransaction = transactionRepository.findById(transaction.getId())
                    .orElseThrow(() -> new Exception("Transaction not found"));

            User user = existingTransaction.getUser();

            // Revert the old transaction amount
            double newTotalAmount = user.getTotalAmount() + existingTransaction.getAmount();

            // Apply the new transaction amount
            newTotalAmount -= transaction.getAmount();

            if (newTotalAmount < 0) {
                throw new Exception("Transaction not successful: insufficient funds.");
            }

            // Save the updated transaction
            Transaction updatedTransaction = transactionRepository.save(transaction);

            // Update the user's total amount
            user.setTotalAmount(newTotalAmount);
            userRepository.save(user);

            return updatedTransaction;
        } catch (Exception e) {
            throw new Exception("Error updating transaction: " + e.getMessage());
        }
    }

    public void deleteTransaction(Long id) throws Exception {
        try {
            transactionRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Error deleting transaction: " + e.getMessage());
        }
    }
}