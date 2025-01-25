package com.ecommerce.project.service;

import com.ecommerce.project.paylod.CartDTO;
import com.ecommerce.project.paylod.ProductDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(ProductDTO productDTO, Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getUserCarts(String email);

    CartDTO getCart(String emailId, Long cartId);

    CartDTO updateProductQuantity(Long productId, int update);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);
}
