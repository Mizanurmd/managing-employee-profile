package com.employeeManagement.service;

import com.employeeManagement.dto.AddressDto;
import com.employeeManagement.responseDto.AddressResponseDto;
import com.employeeManagement.model.Address;

import java.util.Optional;

public interface AddressService {
    AddressResponseDto saveAddress(AddressDto addressDto);

    Address updateAddress(Long addressId, AddressDto addressDto);

    Optional<Address> getAddressById(Long addressId);

    Optional<Address> deleteById(Long addressId);
}
