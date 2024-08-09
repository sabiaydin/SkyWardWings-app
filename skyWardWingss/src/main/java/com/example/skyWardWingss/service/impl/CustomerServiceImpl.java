package com.example.skyWardWingss.service.impl;

import com.example.skyWardWingss.dao.entity.Customer;
import com.example.skyWardWingss.model.exceptions.child.CustomerNotFoundException;
import com.example.skyWardWingss.mapper.CustomerMapper;
import com.example.skyWardWingss.model.dto.request.CustomerRequestDto;
import com.example.skyWardWingss.model.dto.response.CustomerResponse;
import com.example.skyWardWingss.dao.repository.CustomerRepository;
import com.example.skyWardWingss.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse update(Long id, CustomerRequestDto customerRequestDto) {
        log.info("Update method for customer with id = {} is started", id);
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        Customer updatedCustomer = customerMapper.toCustomer(customerRequestDto);
        updatedCustomer.setId(existingCustomer.getId());
        Customer savedCustomer = customerRepository.save(updatedCustomer);
        log.info("Customer with id = {} updated successfully", savedCustomer.getId());
        return customerMapper.toCustomerDto(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("Delete method for customer with id = {} is started", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
        log.info("Customer with id = {} deleted successfully", id);
    }

    @Override
    public CustomerResponse getById(Long id) {
        log.info("Get method for customer by id = {} is started", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        log.info("Customer found with id = {}", id);
        return customerMapper.toCustomerDto(customer);
    }

    @Override
    public Page<CustomerResponse> getAll(Pageable pageable) {
        log.info("Get all method for customers is started");
        Page<CustomerResponse> customerPage = customerRepository.findAll(pageable)
                .map(customerMapper::toCustomerDto);
        log.info("Found {} customers", customerPage.getNumberOfElements());
        return customerPage;
    }
}
