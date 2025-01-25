package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    @NotBlank
    @Size(min = 5,message = "Street name should not be less than 5 characters")
    private String streetName;

    @NotBlank
    @Size(min = 5,message = "Building name should not be less than 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 4,message = "City name should not be less than 4 characters")
    private String city;

    @NotBlank
    @Size(min = 2,message = "State name should not be less than 2 characters")
    private String state;

    @NotBlank
    @Size(min = 2,message = "Country name should not be less than 2 characters")
    private String country;

    @NotBlank
    @Size(min = 6,message = "Pincode should not be less than 5 characters")
    private String pincode;

    //many to many mapping with the users
    @ToString.Exclude//will not include users in address
    @ManyToMany(mappedBy = "addresses")
    private List<User>users = new ArrayList<>();

    public Address(String streetName, String buildingName, String city, String state, String country, String pincode) {
        this.streetName = streetName;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
