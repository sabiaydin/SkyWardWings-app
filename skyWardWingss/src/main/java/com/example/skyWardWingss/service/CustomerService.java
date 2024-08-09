package com.example.skyWardWingss.service;

import com.example.skyWardWingss.model.dto.request.CustomerRequestDto;
import com.example.skyWardWingss.model.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    CustomerResponse update(Long id, CustomerRequestDto customerRequestDto);

    void deleteCustomer(Long id);

    CustomerResponse getById(Long id);

    Page<CustomerResponse> getAll(Pageable pageable);
}
