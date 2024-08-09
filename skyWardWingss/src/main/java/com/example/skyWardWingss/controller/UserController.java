package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.dto.request.ChangePasswordDto;
import com.example.skyWardWingss.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(HttpServletRequest request,
                                                 @RequestBody @Valid ChangePasswordDto changePasswordDto){
      return   userService.changePassword(request, changePasswordDto);
    }
}
