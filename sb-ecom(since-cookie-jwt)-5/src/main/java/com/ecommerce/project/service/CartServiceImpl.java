package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.paylod.CartDTO;
import com.ecommerce.project.paylod.ProductDTO;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired//to know to which user's cart do we add product to -> use authenticated users cart
    private AuthUtil authUtil;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(ProductDTO productDTO, Long productId, Integer quantity) {
        //1. create cart or find existing cart=> as only 1 cart can exist

        Cart cart = createCart();

        //2. fetch product details from database
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        CartItem savedCartItem = cartItemRepository.findCartItemByCartIdAndProductId(cart.getCartId(), productId);
        if(savedCartItem != null){
            throw new APIException("Product " + product.getProductName() + " already exists in the cart");
        }

        //3. perform validation=> if its already in cart, stock>quantity and so on
        if(product.getProductQuantity() == 0){
            throw new APIException("Product " + product.getProductName() + " is out of stock");
        }

        if(quantity < product.getProductQuantity()){
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getProductQuantity() + ".");
        }

        //4. create cart item
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setDiscount(product.getProductDiscount());
        cartItem.setQuantity(quantity);
        cartItem.setDiscountPrice(product.getSpecialPrice());
        cartItem.setProductPrice(product.getProductPrice());

        //save cart item =>
        cartItemRepository.save(cartItem);

        //we won't update the product quantity yet, will update on order
        product.setProductQuantity(product.getProductQuantity());

        //updating the cart and saving it
        cart.setTotalPrice(cart.getTotalPrice() + quantity * product.getSpecialPrice());
        cartRepository.save(cart);

        //making cartDTO
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        //now we need to convert cart item list of cart to product list of cartDTO
        List<CartItem>cartItems = cart.getCartItems();
        System.out.println(cartItems.size()+ "is the cart items size");

        Stream<ProductDTO>productDTOStream = cartItems.stream().map(
                item -> {
                    ProductDTO newProductDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
                    newProductDTO.setProductQuantity(item.getQuantity());
                    return newProductDTO;
                }
        );

        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;//see readme.md point 1 in this folder to see how did this happen
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart>allCarts = cartRepository.findAll();
        if(allCarts.isEmpty()){
            throw new APIException("No Cart Exists");
        }

        List<CartDTO>cartDTOs = allCarts.stream()
                .map(
                        cart->{
                            CartDTO newCartDTO = modelMapper.map(cart, CartDTO.class);
                            List<ProductDTO>productDTOS = cart.getCartItems().stream()
                                    .map(p-> modelMapper.map(p.getProduct(), ProductDTO.class)).toList();

                            newCartDTO.setProducts(productDTOS);
                            return newCartDTO;
                        }
                ).toList();

        return cartDTOs;
    }

    @Override
    public CartDTO getUserCarts(String email) {
        Cart cart = cartRepository.findCartByEmail(email);
        if(cart == null){
            throw new ResourceNotFoundException("Cart", "email", email);
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        List<CartItem>cartItems = cart.getCartItems();
        List<ProductDTO>productDTOS = cartItems.stream().map(
                cartItem->{
                    ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                    productDTO.setProductQuantity(cartItem.getQuantity());
                    return productDTO;
                }
        ).toList();
        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);
        if (cart == null){
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(c ->
                c.getProduct().setProductQuantity(c.getQuantity()));
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(products);
        return cartDTO;
    }

    @Transactional//By default, if a runtime exception (unchecked exception) is thrown during the transaction,
    // all changes made during the transaction are rolled back(as we are updating 3 tables below)
    @Override
    public CartDTO updateProductQuantity(Long productId, int quantity) {
        String emailId = authUtil.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(emailId);
        Long cartId  = userCart.getCartId();

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getProductQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getProductQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getProductQuantity() + ".");
        }

        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setDiscount(product.getProductDiscount());
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
        cartRepository.save(cart);
        CartItem updatedItem = cartItemRepository.save(cartItem);
        if(updatedItem.getQuantity() == 0){
            cartItemRepository.deleteById(updatedItem.getCartItemId());
        }


        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO p = modelMapper.map(item.getProduct(), ProductDTO.class);
            p.setProductQuantity(item.getQuantity());
            return p;
        });


        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() -
                (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemRepository.findCartItemByCartIdAndProductId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem savedCartItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId, productId);
        if(savedCartItem == null){
            throw  new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        List<CartItem>allCartItems = cart.getCartItems();
        allCartItems.forEach(cartItem -> {
            if (cartItem.getProduct().getProductId().equals(productId)) {
                cartItem.setProduct(product);
                cartItem.setProductPrice(product.getProductPrice());
                cartItem.setDiscountPrice(product.getSpecialPrice());
                cartItemRepository.save(cartItem);//saving the new and updated cart item
                cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProductPrice() + product.getSpecialPrice()*cartItem.getQuantity());//updating the cart total price
            }
        });
        cartItemRepository.deleteCartItemByCartIdAndProductId(cartId, productId);

    }

    private Cart createCart() {
        //getting if already exists
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart != null) {
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }
}
