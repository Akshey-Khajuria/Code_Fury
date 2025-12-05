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

    // @Override
    // public String register(User user){
    //     if(user.getGoogle_auth_id()!=null && !user.getGoogle_auth_id().isEmpty()){
    //         return registerOAuthUser(user);
    //     } else {
    //         return registerLocal(user);
    //     }
    // }

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


    @Override
    public User upsertGoogleUser(String googleAuthId, String email, String name, String username) {
        // return repo.findByProviderAndProviderId("google", providerId)
        //         .orElseGet(() -> {
        //             User u = new User();
        //             u.setProvider("google");
        //             u.setProviderId(providerId);
        //             u.setEmail(email);
        //             u.setName(name);
        //             u.setRoles(Collections.singleton("ROLE_USER"));
        //             return repo.save(u);
        //         });
        if(repo.findByGoogleAuthId(googleAuthId).isPresent()){
            logger.info("Existing Google user logged in: {}", email);
            return repo.findByGoogleAuthId(googleAuthId).get();
        }

        User user = new User();
        user.setGoogleAuthId(googleAuthId);
        user.setEmail(email);
        user.setName(name);
        user.setUsername(username);
        return repo.save(user);
    }

    // private String registerOAuthUser(User user) {
    //     if(repo.findByGoogle_auth_id(user.getGoogle_auth_id()).isPresent() || !isNewUser(user)){
    //         return Responses.USER_ALREADY_EXISTS;
    //     }
    //     repo.save(user);
    //     logger.info(Responses.REGISTRATION_SUCCESSFUL, user.getEmail());
    //     return Responses.REGISTRATION_SUCCESSFUL;
    // }

}