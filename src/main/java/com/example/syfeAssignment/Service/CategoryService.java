package com.example.syfeAssignment.Service;

import com.example.syfeAssignment.model.*;
import com.example.syfeAssignment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) throws Exception {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new Exception("Error adding category: " + e.getMessage());
        }
    }

    public List<Category> getCategoriesByUser(Long userId) throws Exception {
        try {
            return categoryRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new Exception("Error fetching categories for user: " + e.getMessage());
        }
    }

    public Category updateCategory(Category category) throws Exception {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new Exception("Error updating category: " + e.getMessage());
        }
    }

    public void deleteCategory(Long id) throws Exception {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Error deleting category: " + e.getMessage());
        }
    }
}