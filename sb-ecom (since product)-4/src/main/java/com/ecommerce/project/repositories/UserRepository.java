package com.ecommerce.project.repositories;

import com.ecommerce.project.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
//<T> ScopedValue<T> findByUserName(String username);//this was the original method which got created by jpa, we changed it to optional!!
    Optional<User> findByUserName(String username);

    boolean existsByUserName(@NotBlank @Size(min = 3, max = 20) String username);

    boolean existsByEmail(@NotBlank @Size(max = 50) @Email String email);
}
