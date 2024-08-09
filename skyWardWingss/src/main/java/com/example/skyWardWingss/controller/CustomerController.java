package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.dto.request.CustomerRequestDto;
import com.example.skyWardWingss.model.dto.request.UserRequest;
import com.example.skyWardWingss.model.dto.response.CustomerResponse;
import com.example.skyWardWingss.service.AuthenticationService;
import com.example.skyWardWingss.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final AuthenticationService authenticationService;
    private final CustomerService customerService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRequest userRequest){
        authenticationService.register(userRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody CustomerRequestDto customerRequestDto) {
        CustomerResponse updatedCustomer = customerService.update(id, customerRequestDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse customerResponse = customerService.getById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(Pageable pageable) {
        Page<CustomerResponse> customers = customerService.getAll(pageable);
        return ResponseEntity.ok(customers);
    }

}
