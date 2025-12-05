package com.voltx.codefury.controller;

import com.voltx.codefury.service.UserService;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.voltx.codefury.constants.Responses;
import com.voltx.codefury.entity.User;

import java.util.Map;
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

    @PostMapping("/register")
    public ResponseEntity<String> registerLocalUser(@RequestBody User user) {
        try{
            logger.info("Registration requested for {}", user.getEmail());
            String response = userService.registerLocal(user);

            if(!Responses.REGISTRATION_SUCCESSFUL.equals(response)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
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
    
}
