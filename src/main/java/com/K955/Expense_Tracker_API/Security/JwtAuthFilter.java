package com.K955.Expense_Tracker_API.Security;

import com.K955.Expense_Tracker_API.Entity.User;
import com.K955.Expense_Tracker_API.Exception.ResourceNotFoundException;
import com.K955.Expense_Tracker_API.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthUtil jwtAuthUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Incoming Request: {}", request.getRequestURI());

        final String requestHeaderToken = request.getHeader("Authorization");

        if(requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestHeaderToken.substring(7);

        String username = jwtAuthUtil.extractUsername(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new ResourceNotFoundException(username, "User"));

            if(jwtAuthUtil.isTokenValid(token, user)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
