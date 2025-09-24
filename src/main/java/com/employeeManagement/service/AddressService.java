package com.employeeManagement.service;

import com.employeeManagement.dto.AddressDto;
import com.employeeManagement.responseDto.AddressResponseDto;
import com.employeeManagement.model.Address;

import java.util.Optional;

public interface AddressService {
    Address saveAddress(AddressDto addressDto);

    Optional<Address> addressById(long addressId);

    Address updateAddress(long addressId, AddressDto addressDto);

    Optional<Address> deleteById(long addressId);
}
