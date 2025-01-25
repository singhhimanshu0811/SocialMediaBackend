package com.ecommerce.project.controller;
import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.paylod.CategoryDTO;
import com.ecommerce.project.paylod.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    @GetMapping("/echo")
//    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", defaultValue = "HelloWorld") String message) {
//        //public ResponseEntity<String> echoMessage(@RequestParam(name = "message") String message) {
//        //public ResponseEntity<String> echoMessage(@RequestParam(name = "message", required = false) String message) {
//        return new ResponseEntity<>(message, HttpStatus.OK);
//        //1st commented func sign-default value specified in case name is not specified, then you'll get default as param, otherwise bad request
//        //2nd commented func sign - no error, you'll get null value
//    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            //default value is in appconfig->app constants//we want all constants to be in same place
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false)String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORIES_ORDER, required = false)String sortOrder) {
        /*
        * PLEASE NOTE THAT WHEN REQUIRED=FALSE DOESN'T MEAN THAT GET WILL FUNCTION WITHOUT PAGINATION. IF YOU
        * PUT REQUIRED=FALSE AND NO DEFAULT VALUE, THEN PARAM=NULL, AND YOU'LL GET :-
        * SERVER ERROR  as null is not int
        * */
    //public ResponseEntity<List<Category>> getAllCategories() {  /changed for dto
        CategoryResponse categories = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryDTOSaved = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(categoryDTOSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO>deleteCategory(@PathVariable long categoryId) {
        CategoryDTO categoryDTO = categoryService.deleteCategory(categoryId);
        //validations are being handled in service now, so dont need try catch here at all
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable long categoryId, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}