package com.ecommerce.project.service;

import com.ecommerce.project.model.User;
import com.ecommerce.project.paylod.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> createAddress(User user, List<AddressDTO> addressDTOS);
}
