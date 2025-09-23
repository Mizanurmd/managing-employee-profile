package com.employeeManagement.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {
    private Long addressId;
    private String street;
    private String city;
    private String state;
    private String presentAddress;
    private String permanentAddress;
}
