package com.voltx.codefury.controller;

import com.voltx.codefury.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.voltx.codefury.auth.security.JwtUtil;
import com.voltx.codefury.constants.Responses;
import com.voltx.codefury.entity.User;

import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    // public ResponseEntity<String> registerLocalUser(@RequestBody User user) {
    public ResponseEntity<String> registerLocalUser(@RequestBody User user, HttpServletResponse httpResponse) {
        try{
            logger.info("Registration requested for {}", user.getEmail());
            String response = userService.registerLocal(user);

            if(!Responses.REGISTRATION_SUCCESSFUL.equals(response)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            userService.findByEmail(user.getEmail()).ifPresent(created -> addSessionCookie(httpResponse, created));
        
            return ResponseEntity.ok(response);
        } catch(DuplicateKeyException e){
            logger.error("Database access error during registration for {}: {}", user.getEmail(), e.getMessage());
            return ResponseEntity.status(500).body(Responses.USER_ALREADY_EXISTS);
        } catch (MongoWriteException mwx) {
            if (mwx.getCode() == 121) {
                logger.warn("Document validation failed: {}", mwx.getError().getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + mwx.getError().getMessage());
            }
            logger.error("Mongo write error", mwx);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database write error");

        } catch (DataIntegrityViolationException div) {
            logger.warn("Data integrity violation: {}", div.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity error: " + div.getMessage());

        } catch (IllegalArgumentException | NullPointerException iae) {
            logger.warn("Bad request during registration: {}", iae.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + iae.getMessage());

        } catch (Exception ex) {
            logger.error("Unexpected error during registration", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletResponse httpResponse) {
        logger.info("Login requested for {}", user.getEmail());
        try{
            String response = userService.loginLocal(user);
            if(!Responses.LOGIN_SUCCESSFUL.equals(response)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            userService.findByEmail(user.getEmail()).ifPresent(loggedIn -> addSessionCookie(httpResponse, loggedIn));
            return ResponseEntity.ok(response);
        } catch(Exception ex){
            logger.error("Unexpected error during login", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    private void addSessionCookie(HttpServletResponse response, User user) {
        // String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        Cookie cookie = new Cookie("SESSION", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtUtil.getExpirationMs() / 1000));
        // cookie.setSecure(true);
        cookie.setSecure(false); // Set to false for development; true for production
        response.addCookie(cookie);
    }
}
