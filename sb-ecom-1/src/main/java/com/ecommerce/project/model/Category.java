package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//i want each instance of category to be in table, so entity annotation here
@Entity(name = "categories")//table name in database
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id//this needs to be added 1 line above our primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    //@NotBlank(message = "Category name can't be blank.")
    @Size(min = 3, message = "what the fuck")
    private String categoryName;

//    public Category() {}
//    //good practice to use constant constructor while using jpa
//
//    public Category(String categoryName, Long categoryId) {
//        this.categoryName = categoryName;
//        this.categoryId = categoryId;
//    }
//
//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }

    //these setters and getters are important for jpa. without them jpa won't work, api won't work, and
    //you won't see postman response
}
