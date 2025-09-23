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
    public AddressResponseDto saveAddress(AddressDto dto) {
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

        return convertAddressToDto(savedAddress);
    }


    @Override
    public Address updateAddress(Long addressId, AddressDto addressDto) {
        return null;
    }

    @Override
    public Optional<Address> getAddressById(Long addressId) {
        return Optional.empty();
    }

    @Override
    public Optional<Address> deleteById(Long addressId) {
        return Optional.empty();
    }

    // Convert Address into Dto
    public AddressResponseDto convertAddressToDto(Address address) {

        return AddressResponseDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .presentAddress(address.getPresentAddress())
                .permanentAddress(address.getPermanentAddress())
                .build();
    }
}
