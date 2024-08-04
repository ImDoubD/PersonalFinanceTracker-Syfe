package com.example.syfeAssignment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.syfeAssignment.model.Category;
import com.example.syfeAssignment.model.User;
import com.example.syfeAssignment.repository.CategoryRepository;
import com.example.syfeAssignment.Service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCategory() throws Exception {
        Category category = new Category();
        category.setName("Food");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.addCategory(category);
        assertNotNull(savedCategory);
    }

    @Test
    public void testGetCategoriesByUser() throws Exception {
        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setUser(user);

        when(categoryRepository.findByUserId(1L)).thenReturn(Collections.singletonList(category));

        List<Category> categories = categoryService.getCategoriesByUser(1L);
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Entertainment");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category updatedCategory = categoryService.updateCategory(category);
        assertNotNull(updatedCategory);
        assertEquals("Entertainment", updatedCategory.getName());
    }

    @Test
    public void testDeleteCategory() throws Exception {
        doNothing().when(categoryRepository).deleteById(1L);
        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
