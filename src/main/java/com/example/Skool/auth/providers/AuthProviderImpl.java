package com.example.Skool.auth.providers;

import com.example.Skool.auth.entities.UserDetailsImpl;
import com.example.Skool.auth.entities.UserPrincipal;
import com.example.Skool.auth.services.UserDetailsServiceImpl;
import com.example.Skool.common.exceptions.SkoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthProviderImpl implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetailsImpl userDetails = (UserDetailsImpl)this.userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new SkoolException("Invalid credentials", this.getClass().toString(), HttpStatus.BAD_REQUEST);
        UserPrincipal userPrincipal = new UserPrincipal(userDetails.getUserId(), userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userPrincipal, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
