package com.microservice.ApiGateway.filter;

import com.microservice.ApiGateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final List<String> OPEN_PATHS = List.of(
            "/auth/login",
            "/auth/register",
            "/actuator"
    );

    private static final List<String> ADMIN_ONLY_PATHS = List.of(
            "/auth/users",
            "/question/allQuestions",
            "/question/add",
            "/auth/register/admin",
            "/question/category",
            "/question/generate",
            "/quiz/create"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean isOpen = OPEN_PATHS.stream().anyMatch(path::startsWith);
        if (isOpen) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        String role;

        try {
            Claims claims = jwtUtil.validateToken(token);
            role = claims.get("role", String.class);
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired token");
            return;
        }
        boolean isAdminPath = ADMIN_ONLY_PATHS.stream().anyMatch(path::startsWith);
        if (isAdminPath && !"ADMIN".equals(role)) {
            sendError(response, HttpServletResponse.SC_FORBIDDEN,
                    "Access denied: ADMIN role required for " + path);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response,
                           int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"status\":" + status + "," +
                        "\"message\":\"" + message + "\"}"
        );
    }
}