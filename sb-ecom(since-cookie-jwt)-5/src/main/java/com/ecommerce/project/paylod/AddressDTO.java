package com.ecommerce.project.paylod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long addressId;
    private String streetName;
    private String buildingName;
    private String city;
    private String state;
    private String zipCode;
    private String country;

}
