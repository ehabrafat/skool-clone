package com.example.Skool.auth.repositories;

import com.example.Skool.auth.entities.UserCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCreatorRepository extends JpaRepository<UserCreator, Integer> {

    Optional<UserCreator> findByUsername(String username);
}
