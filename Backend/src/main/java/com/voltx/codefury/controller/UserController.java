package com.voltx.codefury.controller;

import com.voltx.codefury.service.UserService;

import com.voltx.codefury.entity.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // @PostMapping("/register")
    // public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
        // String email = body.get("email");
        // String pass = body.get("password");
        // String name = body.getOrDefault("name", "");
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        String email = user.getEmail();
        String pass = user.getPassword_hash();
        String name = user.getName();
        User created = userService.registerLocal(email, pass, name);
        // do not return password
        created.setPassword_hash(null);
        return ResponseEntity.ok(created);
    }
}
