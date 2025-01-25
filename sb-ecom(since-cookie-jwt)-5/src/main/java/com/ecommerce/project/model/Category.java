package com.ecommerce.project.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "categories")//table name in database
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id//this needs to be added 1 line above our primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Size(min = 5, message = "Category name must contain at least 5 letters.")
    private String categoryName;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private List<Product>products;

    public void setProducts(List<Product> productObject) {
        productObject.forEach(product -> product.setProductCategory(this));
        this.products = productObject;
    }
}
