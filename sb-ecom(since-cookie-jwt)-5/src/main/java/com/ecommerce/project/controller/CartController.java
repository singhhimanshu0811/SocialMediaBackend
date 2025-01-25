package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.paylod.CartDTO;
import com.ecommerce.project.paylod.ProductDTO;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO>addProductToCart(@RequestBody ProductDTO productDTO, @PathVariable Long productId, @PathVariable Integer quantity) {

        CartDTO cartDTO = cartService.addProductToCart(productDTO, productId, quantity);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO>allCarts = cartService.getAllCarts();
        return new ResponseEntity<>(allCarts,HttpStatus.OK);
    }

    @GetMapping("/carts/users/cart")//get the carts of logged in user
    public ResponseEntity<CartDTO>getUserCarts() {
        String emailId = authUtil.loggedInEmail();
        CartDTO cartDTO = cartService.getUserCarts(emailId);
        return new ResponseEntity<>(cartDTO,HttpStatus.OK);
    }

    @GetMapping("/carts/users/cartScaled")//in future if our application scales and our users might have multiple carts!!
    //so we should also use cart id for fetching cart of a user here
    public ResponseEntity<CartDTO> getCartById(){
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO = cartService.getCart(emailId, cartId);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/api/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO>updateProductQuantity(@PathVariable Long productId, @PathVariable String operation) {
        CartDTO cartDTO = cartService.updateProductQuantity(productId, operation.equalsIgnoreCase("delete")?-1:1);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/carts/{cartId}/product/{productId}")
    public ResponseEntity<String>deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
