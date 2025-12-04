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
    public String registerLocal(String email, String rawPassword, String name){
        if(repo.findByEmail(email).isPresent()){
            logger.warn(Responses.EMAIL_ALREADY_IN_USE, email);
            return Responses.EMAIL_ALREADY_IN_USE;            
        }
        User u = new User();
        u.setEmail(email);
        u.setPassword_hash(encoder.encode(rawPassword));
        u.setName(name);
        repo.save(u);
        logger.info(Responses.REGISTRATION_SUCCESSFUL, email);
        return Responses.REGISTRATION_SUCCESSFUL;
    }

    public Optional<User> findByEmail(String email){
        return repo.findByEmail(email);
    }
}