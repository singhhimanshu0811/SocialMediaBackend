package com.ecommerce.project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "roles")//name of table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name="role_column")
    private Integer roleId;

    @Enumerated(EnumType.STRING)//this enum will be persisted as a string in database, not a number
    @Column(length = 20, name="role_name")
    @NonNull//it will generate constructor of just role name with @RequiredArgsConstructor. roleid wont be there as it is not nonnull
    @ToString.Exclude
    private AppRole roleName;

}
