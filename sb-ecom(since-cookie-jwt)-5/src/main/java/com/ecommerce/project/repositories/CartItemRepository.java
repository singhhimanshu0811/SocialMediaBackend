package com.ecommerce.project.repositories;

import com.ecommerce.project.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci from CartItem ci where ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    CartItem findCartItemByCartIdAndProductId(Long cartId, Long productId);

    @Modifying//self prompt => when updating the database
    @Query("DELETE FROM CartItem ci where ci.cart.cartId = ?1 AND ci.product.productId = ?2")
    void deleteCartItemByCartIdAndProductId(Long cartId, Long productId);
}
