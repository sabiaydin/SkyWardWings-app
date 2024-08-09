package com.example.skyWardWingss.service;

import com.example.skyWardWingss.dao.entity.Authority;
import com.example.skyWardWingss.dao.entity.User;
import com.example.skyWardWingss.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<String> roles = new ArrayList<>();
        Set<Authority> authorities = user.getAuthorities();
        for (Authority authority : authorities) {
            roles.add(authority.getName());
        }

        UserDetails userDetails;
        userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
        return userDetails;
    }
}
