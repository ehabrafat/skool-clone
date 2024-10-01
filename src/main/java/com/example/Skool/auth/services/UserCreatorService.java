package com.example.Skool.auth.services;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.auth.repositories.UserCreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserCreatorService {
    private final UserCreatorRepository userCreatorRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreator register(UserCreator user)  {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userCreatorRepository.save(user);
        user.setPassword(null);
        return user;
    }

    public Optional<UserCreator> findByUsername(String username) {
        return userCreatorRepository.findByUsername(username);
    }

    public Optional<UserCreator> findById(int id) {
        return userCreatorRepository.findById(id);
    }

    public boolean isExists(int id){
        return this.userCreatorRepository.existsById(id);
    }

}
