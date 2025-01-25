package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.paylod.CategoryDTO;
import com.ecommerce.project.paylod.CategoryResponse;

import java.util.List;
//see from 2nd folder how this file ha schnaged, and then bcoz of this changes in categorrycontroller.java and
//impl in services
public interface CategoryService {
    //List<Category>getAllCategories();
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}