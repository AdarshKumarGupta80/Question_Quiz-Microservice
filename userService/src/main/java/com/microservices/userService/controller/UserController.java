package com.microservices.userService.controller;

import com.microservices.userService.model.User;
import com.microservices.userService.service.JwtService;
import com.microservices.userService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@Valid @RequestBody User user) {
        service.registerStudent(user);
        return new ResponseEntity<>("Student registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody User user) {
        service.registerAdmin(user);
        return new ResponseEntity<>("Admin registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            User dbUser = service.findByUsername(user.getUsername());
            String token = jwtService.generateToken(
                    dbUser.getUsername(),
                    dbUser.getRole()
            );
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("Login Failed", HttpStatus.UNAUTHORIZED);
    }
}