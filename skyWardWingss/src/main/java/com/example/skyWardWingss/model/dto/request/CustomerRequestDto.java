package com.example.skyWardWingss.model.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Data
public class CustomerRequestDto {
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^\\+994[0-9]{9}$", message = "Invalid phone format")
    private String phoneNumber;
}
