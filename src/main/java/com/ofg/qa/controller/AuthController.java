package com.ofg.qa.controller;

import com.ofg.qa.entity.User;
import com.ofg.qa.requests.UserRequest;
import com.ofg.qa.security.JwtTokenProvider;
import com.ofg.qa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequest userRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
//        User user = userService.getUserByUsername(userRequest.getUsername());
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setAccessToken("Bearer " + jwtToken);
//        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
//        authResponse.setUserId(user.getId());
        return "Bearer " + jwtToken;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        if (userService.getUserByUsername(userRequest.getUsername()) != null) {
            return new ResponseEntity<>("username alredy in use", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userService.saveUser(user);

        return new ResponseEntity<>("user registered", HttpStatus.CREATED);
    }
}
