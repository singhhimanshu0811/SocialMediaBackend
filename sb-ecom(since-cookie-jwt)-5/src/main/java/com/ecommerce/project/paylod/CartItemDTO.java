package com.ecommerce.project.paylod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    //representing a single cart item
    private Long cartItemId;
    private CartDTO cartDTO;
    private ProductDTO productDTO;

    private Integer quantity;
    private Double discount;
    private Double productPrice;
    private Double discountPrice;
}
