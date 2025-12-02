package com.voltx.codefury.service.impl;

import com.voltx.codefury.entity.User;
import org.springframework.stereotype.Service;
import com.voltx.codefury.auth.repository.UserRepository;
import com.voltx.codefury.service.UserService;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder){
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public User registerLocal(String email, String rawPassword, String name){
        if(repo.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already in use");
        }
        User u = new User();
        u.setEmail(email);
        u.setPassword_hash(encoder.encode(rawPassword));
        u.setName(name);
        // u.setProvider("local");
        return repo.save(u);
    }

    public Optional<User> findByEmail(String email){
        return repo.findByEmail(email);
    }
}