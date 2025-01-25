package com.ecommerce.project.category;
//mapping that returns all the category
import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import com.ecommerce.project.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
/*
* @RestController indicates that the data returned by each method is written
* straight into the response body instead of rendering a template.
* */
@RequestMapping("/api")
//all methods start with /api, so basically for a single controller, you can use this, where all
//methods of a controller will have it
public class CategoryController {
    //way1=constructor injection
//    private CategoryServiceImpl categoryService;
//
//    public CategoryController(CategoryServiceImpl categoryService) {
//        this.categoryService = categoryService;
//    }

    //way2: field injection
    @Autowired
    private CategoryService categoryService;

    //way3: setter injection
    // @Autowired
    // private CategoryService categoryService;
    // public void setCategoryService(CategoryService categoryService) {
    //     this.categoryService = categoryService;
    // }

    @GetMapping("/public/categories")
    //@RequestMapping(value = "/public/categories" , method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories() {
        //return categoryService.getAllCategories();//when you are not returng as response entity but as a string
        List<Category>categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    //@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
    /* if we dont use @Valid here, then bcoz of notBlank in category.java we get server error(500).
    using @Valid here gives us bad request eroor-  which is 400*/
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {

/*        below when you are returng as a string but not as response entity
          categoryService.createCategory(category);
          return "Category "+ category.getCategoryName() + " created";*/
        categoryService.createCategory(category);

        return new ResponseEntity<>("Category with category id" + category.getCategoryId() + " created successfully"
                , HttpStatus.CREATED);
    }
    //for way 1 and way 2 in delete in categoryservcieimpl class
//    @DeleteMapping("/api/admin/categories/{categoryId}")
//    public String deleteCategory(@PathVariable long categoryId) {
//        String status = categoryService.deleteCategory(categoryId);
//        return status;
//    }
    //for way3 below
    @DeleteMapping("/admin/categories/{categoryId}")
    //@RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<String>deleteCategory(@PathVariable long categoryId) {
        try {
            String response = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(response, HttpStatus.OK);
            //other ways of doing same thing :
            //return ResponseEntity.ok(status);
            //return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
            //you won't get reason if you just use exception rather than responsestatus esception in catch
        }
    }

    @PutMapping("/admin/categories/{categoryId}")
    //@RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@PathVariable long categoryId, @RequestBody Category category) {

        try{
            Category savedCategory = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>("Category with categoryId "+ category.getCategoryId() + " updated", HttpStatus.OK);
        }
        catch(ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}