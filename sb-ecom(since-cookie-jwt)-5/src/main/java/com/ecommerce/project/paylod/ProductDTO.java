package com.ecommerce.project.paylod;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    //for model mapper to work the names of variables must be same in model class and dto class
    private Long productId;
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String productName;
    @Size(min = 6, message = "Product name must contain at least 6 characters")
    private String productDescription;
    private String productImage;
    private Integer productQuantity;
    private Double productPrice;
    private Double specialPrice;
    private Double productDiscount;
}
