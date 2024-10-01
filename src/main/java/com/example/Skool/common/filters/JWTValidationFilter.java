package com.example.Skool.common.filters;

import com.example.Skool.auth.entities.UserPrincipal;
import com.example.Skool.common.constants.AppConstants;
import com.example.Skool.common.exceptions.SkoolException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JWTValidationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            log.info("auth jwt " + request.getServletPath());
            String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
                throw new EOFException("Invalid JWT token");
            }
            String jwtToken = authorizationHeader.split(" ")[1];
            Environment env = getEnvironment();
            String secret = env.getProperty(AppConstants.JWT_SECRET, "6b5c416f362963e3850f84d57d18ffed0fc91ad8");
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwtToken).getPayload();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));
            UserPrincipal userPrincipal = new UserPrincipal((int)claims.get("id"), claims.getSubject());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e){
            String errorMessage = String.format("Invalid JWT token: %s", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(errorMessage);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if(request.getRequestURI().startsWith("/ws")) return true;
        return Arrays.asList(AppConstants.publicRoutes).contains(request.getRequestURI());
    }
}
