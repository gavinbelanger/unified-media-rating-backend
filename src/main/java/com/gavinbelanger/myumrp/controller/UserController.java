package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.dto.AuthResponse;
import com.gavinbelanger.myumrp.dto.LoginRequest;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.security.JwtUtil;
import com.gavinbelanger.myumrp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            String token = jwtUtil.generateToken(registeredUser.getUsername());

            return ResponseEntity.ok(new AuthResponse(
                    token,
                    registeredUser.getUsername(),
                    registeredUser.getEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean valid = userService.checkCredentials(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            if (valid) {
                String token = jwtUtil.generateToken(loginRequest.getUsername());
                User user = userService.getUserByUsername(loginRequest.getUsername());

                return ResponseEntity.ok(new AuthResponse(
                        token,
                        user.getUsername(),
                        user.getEmail()
                ));
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}