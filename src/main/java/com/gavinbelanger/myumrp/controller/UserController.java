package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return "User registered successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        try {
            boolean valid = userService.checkCredentials(user.getUsername(), user.getPassword());
            if (valid) return "Login successful!";
            else return "Invalid credentials";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}



