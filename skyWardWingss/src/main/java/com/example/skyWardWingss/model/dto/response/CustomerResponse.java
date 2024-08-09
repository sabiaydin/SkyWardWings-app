package com.example.skyWardWingss.model.dto.response;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
