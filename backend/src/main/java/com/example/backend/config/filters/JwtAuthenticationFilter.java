package com.example.backend.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.auth.services.JwtService;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //String path = request.getServletPath();

        // allow auth endpoints
        // if (path.startsWith("/auth")) {
        //     filterChain.doFilter(request, response);
        //     return;
        // }

        // String header = request.getHeader("Authorization");

        // if (header == null || !header.startsWith("Bearer ")) {
        //     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //     return;
        // }

        // String token = header.substring(7);

        // if (!jwtService.isValid(token)) {
        //     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //     return;
        // }

        // // extract user info
        // String email = jwtService.extractUsername(token);
        // String role = jwtService.extractRole(token);
                
        // List<GrantedAuthority> authorities = List.of(
        //         new SimpleGrantedAuthority("ROLE_" + role)
        // );

        // UsernamePasswordAuthenticationToken auth =
        //         new UsernamePasswordAuthenticationToken(
        //                 email,
        //                 null,
        //                 authorities
        //         );

        // SecurityContextHolder.getContext().setAuthentication(auth);

        // filterChain.doFilter(request, response);

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            if (jwtService.isValid(token)) {

                String email = jwtService.extractUsername(token);
                String role = jwtService.extractRole(token);

                List<GrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority("ROLE_" + role));

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // ALWAYS continue
        filterChain.doFilter(request, response);
    }
}
