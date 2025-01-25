package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name"),
                @UniqueConstraint(columnNames = "email_id")
//see here we are not specifying which column right, just telling column name. now how does jpa know on which column does unique constraint need to be specified?
//see what happens, if your variable name is userName, jpa converts that as column name in table, "user_name", so if you use user_name, in columnNames above, you won't get error
//but if you use, "username" in place of "user_name", you'll get error saying theis column not found. then you;ll have to give "username" name to the column, using @column annotation
//see here if you use anything other than email_id, you'd get error. so see we have used email_id
//as a general good practice,always give column names and you can use them
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")//dont need this, this the way it is default saved in database
    private Long userId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "user_name")//dont need this, this the way it is default saved in database
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email_id")
    private String email;


    @NotBlank
    @Size(max = 100, message = "password cant be bigger than 100")// this is the length of encoded password being stored in db
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
            //eager: whenever you load user, role is also loaded
            //since cascade is written in this file, bcoz of it, changes are made from user to role
    )
    //now user can invoke setRole method, where for a user he might have just change role, and nothing else. so we put
    //getter and setter there
    @Getter
    @Setter
    private Set<Role> roles = new HashSet<>();

    //for buyer side, we can manage products from order
    //but from seller side, we need to link user , who is also a seller to products
    //in the owner table, we can just add user, who will have his role also specified, basically making product the owner
    @ToString.Exclude//will not include this in the two string method
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, mappedBy = "sellerUser")
    //orphan removal is different from cascade delete. as even if you dis-associate products from user, those products will be deleted.
    // but cascade delete is only triggered when user is deleted
    private Set<Product>products = new HashSet<>();

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_addresses",
      joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address>addresses = new ArrayList<>();
    //mapped by in address means that address is the owner of the relationship,//as it has the relationship maintained in its table
    // and if it were say one to many or many to one
    //or one to one, then you'll have user id as foreign key in address table
    //also please see that you cannot use mapped by and join table in many to many relationship in the same table
}
