package com.voltx.codefury.auth.security;

import com.voltx.codefury.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attrs = oauthUser.getAttributes();
        // Google attributes: sub, email, name
        String providerId = (String) attrs.get("sub");
        String email = (String) attrs.get("email");
        String name = (String) attrs.get("name");
        String userName = email.split("@")[0];

        var user = userService.upsertGoogleUser(providerId, email, name, userName);
        // String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());


        Cookie cookie = new Cookie("SESSION", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int)(jwtUtil.getExpirationMs() / 1000)); // you might need getter for expirationMs
        // in production: cookie.setSecure(true);
        cookie.setSecure(false);

        response.addCookie(cookie);

        // redirect to frontend app (change URL)
        response.sendRedirect("/"); // or to client application
    }
}
