package com.microservices.userService.controller;

import com.microservices.userService.model.User;
import com.microservices.userService.service.JwtService;
import com.microservices.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("register")
    public User register(@RequestBody User user) {
        return service.saveUser(user);
    }

    @GetMapping("register")
    public List<User> getUsers() {
        return service.getAllUsers();
    }

    @PostMapping("login")
    public String login(@RequestBody User user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));


        if (authentication.isAuthenticated()) {

            User dbUser = service.findByUsername(user.getUsername());

            return jwtService.generateToken(
                    dbUser.getUsername(),
                    dbUser.getRole()
            );
        }

        return "Login Failed";
    }
}