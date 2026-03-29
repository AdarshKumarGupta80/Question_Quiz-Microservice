package com.microservices.userService.service;


import com.microservices.userService.dao.UserRepo;
import com.microservices.userService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepo repo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("STUDENT");
        System.out.println(user.getPassword());
        return repo.save(user) ;
    }
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}