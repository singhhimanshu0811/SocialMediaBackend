package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.paylod.CategoryDTO;
import com.ecommerce.project.paylod.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired//field injection
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    //private Long idCount = 1L;
    //we dont need categoryId now, as we are generating it in the database level, using primary key itself
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    //public List<Category> getAllCategories() {//changed for dto

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //List<Category>allCategories = categoryRepository.findAll(); //-> no pagination, below 3(4) lines is pagination

        //Pageable pageDetails = PageRequest.of(pageNumber, pageSize);//from jpa->no sorting
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
//      System.out.println(categoryPage.getSize()+" is the size of categoryPage variable");
        List<Category> allCategories = categoryPage.getContent();
//REMEMBER THAT PAGE NUMBER STARTS FROM 0. 0TH PAGE IS 1ST PAGE. if for 30 entries, pageSize=10, if you enter pageSize=3, then you are querying 4th page, which is empty, and you'll get 404 saying empty category!!!!


        if (allCategories.isEmpty()) {
            throw new APIException("Category List is empty. Please add a category first");
        }
        List<CategoryDTO> categoryDTOS = allCategories.stream().
                map(category -> modelMapper.map(category, CategoryDTO.class)).
                toList();
        //using stream as we have list of multiple categories
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setIsLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
        }
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    //we were throwing resource not found in response entity bcoz there is no special class for it
    public CategoryDTO deleteCategory(Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(savedCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        savedCategory.setCategoryName(category.getCategoryName());
        savedCategory = categoryRepository.save(savedCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}