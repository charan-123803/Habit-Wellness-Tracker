package com.lifesync.service;

import com.lifesync.dto.ApiResponse;
import com.lifesync.dto.AuthDTOs;
import com.lifesync.entity.Role;
import com.lifesync.entity.User;
import com.lifesync.repository.RoleRepository;
import com.lifesync.repository.UserRepository;
import com.lifesync.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public ApiResponse<AuthDTOs.JwtResponse> login(AuthDTOs.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(authentication.getName())
                .orElseGet(() -> userRepository.findByEmail(authentication.getName()).get());

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        AuthDTOs.JwtResponse jwtResponse = AuthDTOs.JwtResponse.builder()
                .accessToken(jwt)
                .tokenType("Bearer")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .xpPoints(user.getXpPoints())
                .userLevel(user.getUserLevel())
                .coins(user.getCoins())
                .roles(roles)
                .build();

        return ApiResponse.<AuthDTOs.JwtResponse>builder()
                .success(true)
                .message("Login successful")
                .data(jwtResponse)
                .build();
    }

    @Transactional
    public ApiResponse<String> register(AuthDTOs.RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ApiResponse.<String>builder()
                    .success(false)
                    .message("Username is already taken!")
                    .build();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.<String>builder()
                    .success(false)
                    .message("Email is already registered!")
                    .build();
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .avatarUrl("https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=250&q=80")
                .xpPoints(100)
                .userLevel(1)
                .coins(50)
                .roles(roles)
                .build();

        userRepository.save(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("User registered successfully!")
                .data("User created")
                .build();
    }
}
