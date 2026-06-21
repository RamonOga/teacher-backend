package com.teacher.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    private final CredentialStore credentialStore;

    public AuthFilter(CredentialStore credentialStore) {
        this.credentialStore = credentialStore;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        // Public endpoints
        if (path.equals("/api/admin/login") || 
            path.equals("/api/admin/setup") || 
            !path.startsWith("/api/admin/")) {
            chain.doFilter(request, response);
            return;
        }

        // Check token
        String token = req.getHeader("X-Admin-Token");
        if (!credentialStore.validateToken(token)) {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"Unauthorized\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
