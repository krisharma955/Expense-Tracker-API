package com.K955.Expense_Tracker_API.Service.ImpL;

import com.K955.Expense_Tracker_API.DTOs.Auth.AuthResponse;
import com.K955.Expense_Tracker_API.DTOs.Auth.LoginRequest;
import com.K955.Expense_Tracker_API.DTOs.Auth.SignupRequest;
import com.K955.Expense_Tracker_API.Entity.User;
import com.K955.Expense_Tracker_API.Exception.BadRequestException;
import com.K955.Expense_Tracker_API.Exception.ResourceNotFoundException;
import com.K955.Expense_Tracker_API.Mapper.UserMapper;
import com.K955.Expense_Tracker_API.Repository.UserRepository;
import com.K955.Expense_Tracker_API.Security.JwtAuthUtil;
import com.K955.Expense_Tracker_API.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpL implements AuthService {

    private final UserRepository userRepository;
    private final JwtAuthUtil jwtAuthUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public AuthResponse signup(SignupRequest request) {
        Boolean exists = userRepository.existsByEmail(request.email());
        if(exists) throw new BadRequestException("User with email: " +request.email()+ " already exists");

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
        user.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);

        String token = jwtAuthUtil.generateAccessToken(user);

        return new AuthResponse(token, userMapper.toUserProfileResponse(saved));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (Exception e) {
            throw new BadRequestException("Invalid Email or Password");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException(request.email(), "User"));

        String token = jwtAuthUtil.generateAccessToken(user);

        return new AuthResponse(token, userMapper.toUserProfileResponse(user));
    }

}
