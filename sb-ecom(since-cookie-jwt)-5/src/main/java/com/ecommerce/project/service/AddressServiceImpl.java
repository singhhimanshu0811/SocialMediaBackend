package com.ecommerce.project.service;

import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.paylod.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public List<AddressDTO> createAddress(User user, List<AddressDTO> addressDTOS) {//you cant use for each here, as for each returns void
        //used map as it returns stream
       List<AddressDTO>addressDTOs = addressDTOS.stream()
               .map(addressDTO -> {
                   Address address = modelMapper.map(addressDTO, Address.class);
                   address.getUsers().add(user);
                   user.getAddresses().add(address);
                   Address savedAddress = addressRepository.save(address);
                   return modelMapper.map(savedAddress, AddressDTO.class);
               }).toList();
       return addressDTOS;
    }
}
