package com.voltx.codefury.service;

import com.voltx.codefury.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder){
        this.repo = repo;
        this.encoder = encoder;
    }

    public User registerLocal(String email, String rawPassword, String name){
        if(repo.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already in use");
        }
        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(rawPassword));
        u.setName(name);
        u.setProvider("local");
        u.setRoles(Collections.singleton("ROLE_USER"));
        return repo.save(u);
    }

    public Optional<User> findByEmail(String email){
        return repo.findByEmail(email);
    }
}