package com.example.skyWardWingss.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    private CustomerRequestDto customerRequestDto;
    @NotBlank(message = "Username cannot be empty or null")
    @Size(max = 50)
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "Invalid username input type")
    private String username;
    @NotBlank(message = "Username cannot be empty or null")
    @Size(min = 3)
    @Pattern(regexp = "^[A-Za-z0-9_.]+$", message = "Invalid password input type")
    private String password;
    private String role;
}
