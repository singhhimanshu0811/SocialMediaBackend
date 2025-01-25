package com.ecommerce.project.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categories")//table name in database
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id//this needs to be added 1 line above our primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @NotBlank(message = "Category name can't be blank.")
    @Size(min = 5, message = "Category name must contain at least 5 letters.")
    private String categoryName;

}
