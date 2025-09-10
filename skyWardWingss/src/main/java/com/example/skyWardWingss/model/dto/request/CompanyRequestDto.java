package com.example.skyWardWingss.model.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CompanyRequestDto {
    private String name;
    private String phoneNumber;
    private String address;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^[0-9]{16}$", message = "Bank account must be 16 digits")
    private String bankAccount;
}
