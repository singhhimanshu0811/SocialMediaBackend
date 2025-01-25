package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.paylod.CartDTO;
import com.ecommerce.project.paylod.ProductDTO;
import com.ecommerce.project.paylod.ProductResponse;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")//not hardcoding it, for server/computer paths, we put paths in application properties
    private String imageFilePath;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;

    //private String imageFilePath = "images/"

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Product  product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        boolean isProductInCategory = category.getProducts().stream()
                        .anyMatch(productInstance -> productInstance.getProductName().equals(product.getProductName()));

        if(isProductInCategory){
            throw new APIException("Product already exists in this category!!");
        }

        product.setProductCategory(category);
        double specialPrice = product.getProductPrice()-
                ((product.getProductDiscount()*0.01)*product.getProductPrice());
        product.setSpecialPrice(specialPrice);
        product.setProductImage("default.png");
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Product>productPage = productRepository.findAll(pageDetails);
        List<Product>allProducts = productPage.getContent();
        if(allProducts.isEmpty()){
            throw new APIException("Product List is empty. Please add a product first");
        }
        List<ProductDTO>allProductsDTO = allProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(allProductsDTO);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse getProductByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortBy.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        //List<Product>productByCategory = productRepository.findByProductCategory(category);//jaadu of jpa!!, see below even sorted by price!!!!!!!!
        List<Product>productByCategory = productRepository.findByProductCategoryOrderByProductPriceAsc(category);

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Product>productPage = productRepository.findByProductCategoryOrderByProductPriceAsc(category, pageDetails);//you'll have to pass category also here
        List<Product>allProducts = productPage.getContent();
        if(productByCategory.isEmpty()){
            throw new APIException("No product in this category. Please add a product first");
        }
        List<ProductDTO>productsByCategoryDTO = productByCategory.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(productsByCategoryDTO);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByOrder = sortBy.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Product>pageProduct = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);
        List<Product>productByKeyword = pageProduct.getContent();

        //List<Product>productByKeyword = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        if(productByKeyword.isEmpty()){
            throw new APIException("No product in this category by this category");
        }
        List<ProductDTO>productByKeywordDTO = productByKeyword.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(productByKeywordDTO);
        productResponse.setPageNumber(pageProduct.getNumber());
        productResponse.setPageSize(pageProduct.getSize());
        productResponse.setTotalElements(pageProduct.getTotalElements());
        productResponse.setTotalPages(pageProduct.getTotalPages());
        productResponse.setLastPage(pageProduct.isLast());

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productFromDB.setProductName(product.getProductName() != null ? product.getProductName() : productFromDB.getProductName());
        productFromDB.setProductPrice(product.getProductPrice() != null ? product.getProductPrice() : productFromDB.getProductPrice());
        productFromDB.setProductDescription(product.getProductDescription() != null ? product.getProductDescription() : productFromDB.getProductDescription());

        Product savedProduct = productRepository.save(productFromDB);
        //now after product is updated or deleted, we need to update all carts also where product was there
        List<Cart>allCarts = cartRepository.findByProductId(productId);

        List<CartDTO>allCartsDTO = allCarts.stream().map(
                cart -> {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                    List<ProductDTO>productDTOS = cart.getCartItems().stream().map(
                            cartItem -> modelMapper.map(cartItem.getProduct(), ProductDTO.class)).toList();
                    cartDTO.setProducts(productDTOS);
                    return cartDTO;
                }
        ).toList();

        allCartsDTO.forEach(cartDTO -> cartService.updateProductInCarts(cartDTO.getCartId(), productId));//updating all the products using the cartDTO, as we dont have products in cart, but in cartDTO
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(productFromDB);//similarly like update above, you can update cart table, basically deleting from cart and cartitem repository
        return modelMapper.map(productFromDB, ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
            //1. get product from db
            Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

            //2.upload image to server. here it will mean saving the images in ./images folder and get the name of image file
            String fileName = fileService.uploadImage(imageFilePath, image);
            System.out.print(fileName+" is the og path");
            //3. update the image
            savedProduct.setProductImage(fileName);

            //4 delete old product , saved updated product and return
            Product updatedProduct = productRepository.save(savedProduct);
            productRepository.delete(savedProduct);

            return modelMapper.map(updatedProduct, ProductDTO.class);

    }


}
