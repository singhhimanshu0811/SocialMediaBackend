package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    //The field used in the JPQL query should match the field name in the entity class, not the database column name.
    @Query("SELECT c from Cart c where c.user.email = ?1")
    Cart findCartByEmail(String email);// you can add query like this if you are not sure the jpa will auto generate correct query.

    @Query("SELECT c from Cart c where c.user.email = ?1 AND c.cartId = ?2")
    Cart findCartByEmailAndCartId(String emailId, Long cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.productId = ?1")//join cart ith cart and then with product
    //in sql, select p.productId from Cart c join CartItems ci on c.id = c.cart_id join product p on ci.pid=p.id where p.id = ?1
    List<Cart> findByProductId(Long productId);
    //?1 means first argument.

}
