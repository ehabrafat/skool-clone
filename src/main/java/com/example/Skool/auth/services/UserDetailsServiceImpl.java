package com.example.Skool.auth.services;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.auth.entities.UserDetailsImpl;
import com.example.Skool.auth.repositories.UserCreatorRepository;
import com.example.Skool.common.exceptions.SkoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserCreatorRepository userCreatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCreator user = userCreatorRepository.findByUsername(username).orElseThrow(() -> new SkoolException("Invalid Credentials", this.getClass().toString(), HttpStatus.BAD_REQUEST));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }
}
