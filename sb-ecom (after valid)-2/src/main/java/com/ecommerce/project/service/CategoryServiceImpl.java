package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired//field injection
    private CategoryRepository categoryRepository;

    //private Long idCount = 1L;
    //we dont need categoryId now, as we are generating it in the database level, using primary key itself
    @Override
    public List<Category> getAllCategories() {
        List<Category>allCategories = categoryRepository.findAll();

        if(allCategories.isEmpty()){
            throw new APIException("Category List is empty. Please add a category first");
        }
        return allCategories;
    }

    @Override
    public void createCategory(Category category) {

        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
            //READ COMMENT ON THIS FUNCTION IN CATEGORY REPOSITORY.JAVA -> VERY VERY IMPORTANT
        }
        //category.setCategoryId(idCount++);
        //we dont need categoryId now, as we are generating it in the database level, using primary key itself
        //hence commented line 22 and line 42
        categoryRepository.save(category);
    }

    @Override
    //we were throwing resource not found in response entity bcoz there is no special class for it
    public String deleteCategory(Long categoryId) {
//        Category savedCategory = categoryRepository.findById(categoryId).
//                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        Category savedCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(savedCategory);
        return "Category with id" +  savedCategory.getCategoryId() + " deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
//        Category savedCategory =categoryRepository.findById(categoryId).
//                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        Category savedCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        savedCategory.setCategoryName(category.getCategoryName());
        savedCategory = categoryRepository.save(savedCategory);
        return savedCategory;
    }
}