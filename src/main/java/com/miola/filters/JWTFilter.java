package com.miola.filters;

import com.miola.exceptions.CorruptTokenException;
import com.miola.exceptions.InvalidTokenException;
import com.miola.exceptions.NotLoggedInOrInvalidAuthorizationHeaderException;
import com.miola.messages.ExceptionMessages;
import com.miola.users.CustomUserDetails;
import com.miola.users.CustomUserDetailsService;
import com.miola.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {

        String token = null;
        String email = null;

        String authorization = req.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer ")) {
            try {
                token = authorization.substring(7);
                email = jwtUtil.extractUsername(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken
                                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        req.setAttribute("customException", ExceptionMessages.INVALID_TOKEN);
                    }
                } else {
                    req.setAttribute("customException", ExceptionMessages.CORRUPT_TOKEN);
                }
            } catch (JwtException e) {
                req.setAttribute("customException", e.getMessage());
            }
        } else {
            req.setAttribute("customException", ExceptionMessages.NOT_LOGGED_IN_OR_INVALID_AUTH_HEADER);
        }

        chain.doFilter(req,res);
    }
}
