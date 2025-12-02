package com.voltx.codefury.service;

import com.voltx.codefury.entity.User;
import java.util.Optional;

public interface UserService {
    User registerLocal(String email, String rawPassword, String name);
    Optional<User> findByEmail(String email);
}