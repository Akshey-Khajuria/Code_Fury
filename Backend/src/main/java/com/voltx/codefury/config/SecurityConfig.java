package com.voltx.codefury.config;

import com.voltx.codefury.auth.security.OAuth2SuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfig(OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.ignoringRequestMatchers("/user/login", "/user/register"))
    //         .authorizeHttpRequests(authz -> authz
    //             .requestMatchers("/user/login", "/user/register").permitAll()
    //             .anyRequest().authenticated()
    //         // )
    //         // .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class
    //         );
        
    //     return http.build();
    // }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // for APIs; enable with CSRF protection for browser forms
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/oauth2/**", "/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(a -> a.baseUri("/oauth2/authorize"))
                .redirectionEndpoint(r -> r.baseUri("/oauth2/callback/*"))
                .successHandler(oAuth2SuccessHandler)
            )
            .sessionManagement(s -> s.disable()) // stateless with JWT cookie
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}