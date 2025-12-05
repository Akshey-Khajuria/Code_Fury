package com.voltx.codefury.auth.repository;

import com.voltx.codefury.entity.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByGoogleAuthId(String googleAuthId);
}
