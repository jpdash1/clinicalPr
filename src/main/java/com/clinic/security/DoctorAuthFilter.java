package com.clinic.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DoctorAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();               // e.g. /api/auth/doctor/request-otp or /ctx/api/auth/doctor/request-otp
        String ctx = req.getContextPath();              // e.g. "" or "/ctx"
        String path = uri.startsWith(ctx) ? uri.substring(ctx.length()) : uri; // strip context path
        String method = req.getMethod();

        // TEMP debug logs (remove when stable)
        System.out.println("[AuthFilter] " + method + " " + uri + " (ctx=" + ctx + ", path=" + path + ")");

        // 1) Always allow preflight
        if ("OPTIONS".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }

        // 2) Public endpoints
        boolean isPublic =
                path.startsWith("/api/auth/doctor") ||  // OTP endpoints
                        path.startsWith("/api/clinics")     ||
                        path.startsWith("/api/appointments");

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        // 3) Protected endpoints
        if (path.startsWith("/api/admin") || path.startsWith("/api/doctor")) {
            String auth = req.getHeader("Authorization");
            if (auth == null || !auth.startsWith("Bearer ")) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String token = auth.substring(7);
            try {
                JwtUtil.verify(token);
            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}