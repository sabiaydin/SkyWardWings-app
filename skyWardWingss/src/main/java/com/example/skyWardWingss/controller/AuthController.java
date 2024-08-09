package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.dto.request.LoginRequestDto;
import com.example.skyWardWingss.model.dto.request.RecoveryPassword;
import com.example.skyWardWingss.model.dto.request.UserRequest;
import com.example.skyWardWingss.service.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return authenticationService.authenticate(loginRequestDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> requestPasswordReset(@RequestParam @NotBlank @Email String email){
        return authenticationService.requestPasswordReset(email);
    }

    @PatchMapping("/recovery-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid RecoveryPassword recoveryPassword) {
        return authenticationService.resetPassword(recoveryPassword);
    }
}
