package com.ecommerce.project.controller;

import com.ecommerce.project.model.User;
import com.ecommerce.project.paylod.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    private final AuthUtil authUtil;

    public AddressController(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }
    @Autowired
    AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<List<AddressDTO>>createAddress(@Valid @RequestBody List<AddressDTO> addressDTOS) {
        User user = authUtil.loggedInUser();
        List<AddressDTO>addresses = addressService.createAddress(user, addressDTOS);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }
}
