package com.example.skyWardWingss.service;

import com.example.skyWardWingss.model.dto.request.LoginRequestDto;
import com.example.skyWardWingss.model.dto.request.RecoveryPassword;
import com.example.skyWardWingss.model.dto.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> authenticate(LoginRequestDto loginRequestDto);

    void register(UserRequest userRequest);

    ResponseEntity<String> requestPasswordReset(String email);


    ResponseEntity<String> resetPassword(RecoveryPassword recoveryPassword);
}
