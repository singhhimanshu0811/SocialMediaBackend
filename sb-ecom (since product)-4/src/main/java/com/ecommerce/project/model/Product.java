package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String productName;
    @Size(min = 6, message = "Product name must contain at least 3 characters")
    private String productDescription;
    private Integer productQuantity;
    private String productImage;
    private Double productPrice;
    private Double productDiscount;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private  Category productCategory;

    public void setProductCategory(Category productCategoryObject) {
        this.productCategory = productCategoryObject;

        if(productCategoryObject != null && !productCategoryObject.getProducts().contains(this)) {
            productCategoryObject.getProducts().add(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "seller_id")//see since user id is primary key of user, only this can be foreign key, nothing else.
    //so we need not worry about giving name to joincolumn which column does not exist, unlike in unique constraints
    private User sellerUser;//read note from user.java for seller point of view

}
