package com.senaifatesg.pi2025.auth;

import com.senaifatesg.pi2025.auth.models.AuthResponse;
import com.senaifatesg.pi2025.auth.models.LoginRequest;
import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.enums.UserRoles;
import com.senaifatesg.pi2025.core.models.User;
import com.senaifatesg.pi2025.core.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
     private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager , PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
         this.passwordEncoder = passwordEncoder; 
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword() 
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user.getUsername());
        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(user.getId().toString(), user.getUsername());

        return new AuthResponse(token, userInfo);
    }

    public User registerUser(String username, String password, UserRoles role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BusinessException("Username already exists.");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.addRole(role);
        return userRepository.save(newUser);
    }
}