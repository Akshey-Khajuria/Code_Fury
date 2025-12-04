package com.voltx.codefury.service.impl;

import com.voltx.codefury.entity.User;
import org.springframework.stereotype.Service;
import com.voltx.codefury.auth.repository.UserRepository;
import com.voltx.codefury.constants.Responses;
import com.voltx.codefury.service.UserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder){
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public String registerLocal(User user){
        String rawPassword = user.getPassword();
        if(!isNewUser(user)){
            return Responses.USER_ALREADY_EXISTS;
        }
        user.setPassword_hash(encoder.encode(rawPassword));
        repo.save(user);
        logger.info(Responses.REGISTRATION_SUCCESSFUL, user.getEmail());
        return Responses.REGISTRATION_SUCCESSFUL;
    }

    public Optional<User> findByEmail(String email){
        return repo.findByEmail(email);
    }

    public Optional<User> findByUsername(String username){
        return repo.findByUsername(username);
    }

    private boolean isNewUser(User user){
        String email = user.getEmail();
        String username = user.getUsername();
        if(repo.findByEmail(email).isPresent()){
            logger.warn(Responses.EMAIL_ALREADY_IN_USE, email);
            return false;            
        }
        if(repo.findByUsername(username).isPresent()){
            logger.warn(Responses.USERNAME_ALREADY_IN_USE, username);
            return false;            
        }
        return true;
    }
}