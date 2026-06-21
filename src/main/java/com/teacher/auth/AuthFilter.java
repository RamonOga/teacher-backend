package com.teacher.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthFilter implements Filter {

    private static final String ADMIN_LOGIN = "almark-top";
    private static final String ADMIN_PASS = "School-is-g00d";
    private static final String TOKEN_HEADER = "X-Admin-Token";
    private static final Set<String> validTokens = ConcurrentHashMap.newKeySet();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        // Public endpoints
        if (path.equals("/api/admin/login") || !path.startsWith("/api/admin/")) {
            chain.doFilter(request, response);
            return;
        }

        // Check token
        String token = req.getHeader(TOKEN_HEADER);
        if (token == null || !validTokens.contains(token)) {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"Unauthorized\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    public static String login(String login, String pass) {
        if (!ADMIN_LOGIN.equals(login) || !ADMIN_PASS.equals(pass)) {
            return null;
        }
        String token = generateToken();
        validTokens.add(token);
        return token;
    }

    public static void logout(String token) {
        validTokens.remove(token);
    }

    private static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
