package com.voltx.codefury.service;

import com.voltx.codefury.entity.User;
import java.util.Optional;

public interface UserService {
    public String registerLocal(User user);
    public User upsertGoogleUser(String googleAuthId, String email, String name, String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}