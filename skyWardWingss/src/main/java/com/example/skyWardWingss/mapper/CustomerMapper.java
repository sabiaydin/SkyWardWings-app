package com.example.skyWardWingss.mapper;


import com.example.skyWardWingss.model.dto.request.CustomerRequestDto;
import com.example.skyWardWingss.model.dto.response.CustomerResponse;
import com.example.skyWardWingss.dao.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponse toCustomerDto(Customer customer);

    Customer toCustomer(CustomerRequestDto customerRequestDto);

}
