package com.example.Skool.auth.controllers;

import com.example.Skool.auth.dtos.JwtTokenResponse;
import com.example.Skool.auth.dtos.LoginUserDto;
import com.example.Skool.auth.dtos.RegisterUserDto;
import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.auth.entities.UserPrincipal;
import com.example.Skool.auth.services.JWTService;
import com.example.Skool.auth.services.UserCreatorService;
import com.example.Skool.auth.services.UserDetailsServiceImpl;
import com.example.Skool.common.exceptions.SkoolException;
import com.example.Skool.common.mappers.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserCreatorService userCreatorService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginUserDto.getUsername(), loginUserDto.getPassword());
        Authentication authenticationResult = authenticationManager.authenticate(authentication);
        if(!authenticationResult.isAuthenticated()){
            throw new SkoolException("Invalid Credentials", this.getClass().toString(), HttpStatus.UNAUTHORIZED);
        }
        UserPrincipal userPrincipal = (UserPrincipal)authenticationResult.getPrincipal();
        String jwtToken = jwtService.createJWT(userPrincipal);
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("register")
    public UserCreator register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return userCreatorService.register(userMapper.toUserEntity(registerUserDto));
    }

    @GetMapping("/me")
    public UserCreator me(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return this.userCreatorService.findById(userPrincipal.getId()).orElse(null);
    }


}

