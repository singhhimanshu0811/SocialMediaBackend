package com.ecommerce.project.repositories;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    <T> ScopedValue<T> findByRoleName(AppRole appRole);
    Optional<Role> findByRoleName(AppRole appRole);

}
