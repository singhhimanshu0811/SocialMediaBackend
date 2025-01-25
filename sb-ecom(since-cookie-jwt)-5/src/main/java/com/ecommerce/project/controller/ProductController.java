package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.paylod.ProductDTO;
import com.ecommerce.project.paylod.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;//see that bean is being creted not from interface, but from productImpl class, we dont create bean in interface, but in class

    @PostMapping("/admin/categories/{categoryId}/product")
    //valid for checking name and description validity. pls check the model product
    public ResponseEntity<ProductDTO>addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
        ProductDTO obProductDTO = productService.addProduct(productDTO, categoryId);
        return new ResponseEntity<>(obProductDTO, HttpStatus.OK);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name="pageNumber", required = false, defaultValue = AppConstants.PAGE_NUMBER)Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE)Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY)String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_CATEGORIES_ORDER)String sortOrder
    ) {
        ProductResponse allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse>getProductByCategoryId(@PathVariable Long categoryId,
    @RequestParam(name="pageNumber", required = false, defaultValue = AppConstants.PAGE_NUMBER)Integer pageNumber,
    @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE)Integer pageSize,
    @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY)String sortBy,
    @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_CATEGORIES_ORDER)String sortOrder
    ) {
        ProductResponse productByCategory = productService.getProductByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productByCategory, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse>getProductsByKeyword(@PathVariable String keyword,
    @RequestParam(name="pageNumber", required = false, defaultValue = AppConstants.PAGE_NUMBER)Integer pageNumber,
    @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE)Integer pageSize,
    @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY)String sortBy,
    @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_CATEGORIES_ORDER)String sortOrder){
        ProductResponse productByKeyword = productService.getProductByKeyword(keyword,pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productByKeyword, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO>updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId
    ) {
        ProductDTO updatedProduct = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO>deleteProduct(@PathVariable Long productId) {
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO>updateImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
