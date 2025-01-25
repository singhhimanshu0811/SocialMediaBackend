package com.ecommerce.project.paylod;
//Not mapped to any database table; used for sending/receiving cart-related data in APIs.
//Simplifies the representation of the Cart by omitting low-level details like CartItem and focusing on the Product details directly.

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long cartId;
    private Double totalPrice = 0.0;
    private List<ProductDTO>products = new ArrayList<>(); //see that this is not in cart. there we have list of cart_item
    //also its list of product dto not product
}
