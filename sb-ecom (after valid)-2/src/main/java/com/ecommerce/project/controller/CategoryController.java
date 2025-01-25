package com.ecommerce.project.controller;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category>categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category with category id" + category.getCategoryId() + " created successfully"
                , HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String>deleteCategory(@PathVariable long categoryId) {
        //way 1
//        try {
//            String response = categoryService.deleteCategory(categoryId);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//        catch (ResponseStatusException e) {
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
        //way2: centralizing validation now, so dont need try catch in controller. anyways not a good ideato have validation
        //in controller
        String response = categoryService.deleteCategory(categoryId);
        //validations are being handled in service now, so dont need try catch here at all
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @PathVariable long categoryId, @RequestBody Category category) {
        //way1
//        try{
//            Category savedCategory = categoryService.updateCategory(category, categoryId);
//            return new ResponseEntity<>("Category with categoryId "+ category.getCategoryId() + " updated", HttpStatus.OK);
//        }
//        catch(ResponseStatusException e){
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }

        //way2
        //same as above
        Category savedCategory = categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>("Category with categoryId "+ category.getCategoryId() + " updated", HttpStatus.OK);
    }
}