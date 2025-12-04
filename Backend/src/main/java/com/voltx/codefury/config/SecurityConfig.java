package com.voltx.codefury.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/user/login", "/user/register"))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/user/login", "/user/register").permitAll()
                .anyRequest().authenticated()
            // )
            // .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class
            );
        
        return http.build();
    }
}