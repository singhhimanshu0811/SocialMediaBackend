package com.ecommerce.project.service;

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
    //private List<Category> categories = new ArrayList<>();

    @Autowired//field injection
    private CategoryRepository categoryRepository;

    private Long idCount = 1L;
    @Override
    public List<Category> getAllCategories() {
        //return categories;
        return categoryRepository.findAll();//will return all categories in list format
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(idCount++);
        //categories.add(category);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        //way1
//        try{
//        Category category;
//        category = categories.stream().
//                filter(c->c.getCategoryId().equals(categoryId)).
//                findFirst().get();
//
//        categories.remove(category);
//        return "Category with id" +  category.getCategoryId() + " deleted successfully";
//        }catch (Exception e){
//        return "NO such category id exists. Error: " + e.getMessage();}
//        }
        //way2
//        Category category = categories.stream().
//                filter(c -> c.getCategoryId().equals(categoryId)).findFirst().orElse(null);
//        if(category == null) {
//            return "Category does not exist";
//        }
//        categories.remove(category);
//        return "Category deleted successfully";
// see that in delete above, using orElse just after findAll gives datatype category, but without OrElse
        //you'll get type optional<category>. you can remove orElse and try.

        //way3->MOST CORRECT-WITH RESPONSE CODES
//        List<Category>categories = categoryRepository.findAll();
//        Category category = categories.stream().
//                filter(c -> c.getCategoryId().equals(categoryId)).findFirst().
//                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));//404
//
//        //categories.remove(category);
//        categoryRepository.delete(category);
//        return "Category with id" +  category.getCategoryId() + " deleted successfully";

        //way4
        //again see that when used with exception, findALl or findById gives you type category, otherwise
        //gives you type optional
        //findbyid only in jpa
        Category savedCategory = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        categoryRepository.delete(savedCategory);
        return "Category with id" +  savedCategory.getCategoryId() + " deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        //return null;
        //without using orElseThrow here, as we want to use get here. but if category does not exist,
        // we get error, aas get cant have null. so we use optional, as findFirst returns optional
        //basically if else after whole filter
        //way 1
//        List<Category>categories = categoryRepository.findAll();
//        Optional<Category> optionalCategory = categories.stream().
//                filter(c -> c.getCategoryId().equals(categoryId)).findFirst();
//
//        if(optionalCategory.isPresent()) {
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            Category savedCategory = categoryRepository.save(existingCategory);
//            return savedCategory;
//        }
//        else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
//        }


        //way 2

        Category savedCategory =categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        savedCategory.setCategoryName(category.getCategoryName());
        savedCategory = categoryRepository.save(savedCategory);


        return savedCategory;

    }
}
