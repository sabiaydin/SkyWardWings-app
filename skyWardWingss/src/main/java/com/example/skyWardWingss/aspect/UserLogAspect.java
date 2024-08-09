package com.example.skyWardWingss.aspect;

import com.example.skyWardWingss.dao.entity.User;
import com.example.skyWardWingss.dao.entity.UserLog;
import com.example.skyWardWingss.model.dto.request.LoginRequestDto;
import com.example.skyWardWingss.dao.repository.UserLogRepository;
import com.example.skyWardWingss.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Aspect
@Component
@Slf4j
@Configurable
@RequiredArgsConstructor
public class UserLogAspect {
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;

    @Pointcut("execution(* com.example.skyWardWingss.service.impl.AuthenticationServiceImpl.authenticate(..))")
    public void authenticateMethod() {
    }

    @Before("authenticateMethod()")
    public void logBeforeAuthentication(JoinPoint joinPoint) {
        LoginRequestDto loginRequestDto = (LoginRequestDto) joinPoint.getArgs()[0];
        String username = loginRequestDto.getUsername();
        log.info("Attempting to authenticate user: {}", username);
    }

    @AfterReturning("authenticateMethod()")
    public void logSignIn(JoinPoint joinPoint) {
        LoginRequestDto loginRequestDto = (LoginRequestDto) joinPoint.getArgs()[0];
        String username = loginRequestDto.getUsername();
        log.info("Authentication successful for user: {}", username);

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserLog userLog = new UserLog();
            userLog.setUser(user);
            userLog.setLastLoginDate(LocalDateTime.now());
            userLogRepository.save(userLog);
            log.info("Sign-in logged for user: {}", username);
        } else {
            log.warn("User with username {} not found", username);
        }
    }
}

