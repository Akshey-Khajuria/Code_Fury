package com.voltx.codefury.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.voltx.codefury.auth.security.OAuth2SuccessHandler;
import com.voltx.codefury.enums.Role;
import com.voltx.codefury.config.JwtAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(OAuth2SuccessHandler oAuth2SuccessHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // for APIs; enable with CSRF protection for browser forms
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/oauth2/**", "/public/**", 
                    "/api/problems/all", "/api/problems/byId/**", "/api/problems/byDifficulty/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/problems/add").hasAnyRole(Role.PROBLEM_SETTER.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/problems/solve").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class)

            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(a -> a.baseUri("/oauth2/authorize"))
                .redirectionEndpoint(r -> r.baseUri("/oauth2/callback/*"))
                .successHandler(oAuth2SuccessHandler)
            )
            // .sessionManagement(s -> s.disable()) // stateless with JWT cookie
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            // .httpBasic(Customizer.withDefaults())
            ;

        return http.build();
    }
}