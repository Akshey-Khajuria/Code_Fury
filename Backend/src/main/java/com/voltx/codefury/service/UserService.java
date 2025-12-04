package com.voltx.codefury.service;

import com.voltx.codefury.entity.User;
import java.util.Optional;

public interface UserService {
    public String registerLocal(User user);
    Optional<User> findByEmail(String email);
}