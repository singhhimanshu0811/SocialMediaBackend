package com.ecommerce.project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name="role_column")
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name="role_name")
    @NonNull
    @ToString.Exclude
    private AppRole roleName;

}
