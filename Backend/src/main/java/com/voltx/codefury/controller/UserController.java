package com.voltx.codefury.controller;

import com.voltx.codefury.service.UserService;

import com.voltx.codefury.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        logger.info("Registration requested for {}", user.getEmail());
        String email = user.getEmail();
        String pass = user.getPassword_hash();
        String name = user.getName();
        String response = userService.registerLocal(email, pass, name);
        return ResponseEntity.ok(response);
    }
}
