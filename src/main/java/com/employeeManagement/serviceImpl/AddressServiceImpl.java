package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.AddressDto;
import com.employeeManagement.responseDto.AddressResponseDto;
import com.employeeManagement.model.Address;
import com.employeeManagement.model.Student;
import com.employeeManagement.repository.AddressRepository;
import com.employeeManagement.repository.StudentRepository;
import com.employeeManagement.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, StudentRepository studentRepository) {
        this.addressRepository = addressRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Address saveAddress(AddressDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));

        Address address = Address.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .presentAddress(dto.getPresentAddress())
                .permanentAddress(dto.getPermanentAddress())
                .student(student)
                .build();

        Address savedAddress = addressRepository.save(address);

        return savedAddress;
    }

    @Override
    public Optional<Address> addressById(long addressId) {
        return addressRepository.findById(addressId);
    }

    @Override
    public Address updateAddress(long addressId, AddressDto addressDto) {
        Address existingAddress = addressById(addressId).orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
        existingAddress.setStreet(addressDto.getStreet());
        existingAddress.setCity(addressDto.getCity());
        existingAddress.setState(addressDto.getState());
        existingAddress.setPresentAddress(addressDto.getPresentAddress());
        existingAddress.setPermanentAddress(addressDto.getPermanentAddress());
        Address updatedAddress = addressRepository.save(existingAddress);

        return updatedAddress;
    }


    @Override
    public Optional<Address> deleteById(long addressId) {
        return addressRepository.findById(addressId).map(address -> {
            addressRepository.delete(address);
            return address;
        });
    }


}
