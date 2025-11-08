package com.clinic.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String auth = request.getHeader("Authorization");

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                Claims claims = JwtUtil.verify(token);
                Object doctorId = claims.get("doctorId");
                log.info("[JWT] valid token path={} doctorId={} sub={} exp={}", path, doctorId, claims.getSubject(), claims.getExpiration());

                AbstractAuthenticationToken authentication =
                        new AbstractAuthenticationToken(List.of(new SimpleGrantedAuthority("ROLE_DOCTOR"))) {
                            @Override public Object getCredentials() { return token; }
                            @Override public Object getPrincipal() { return doctorId != null ? doctorId : claims.getSubject(); }
                        };
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                log.warn("[JWT] invalid token path={} reason={}", path, e.getMessage());
                SecurityContextHolder.clearContext();
            }
        } else {
            log.debug("[JWT] no bearer path={}", path);
        }

        chain.doFilter(request, response);
    }
}