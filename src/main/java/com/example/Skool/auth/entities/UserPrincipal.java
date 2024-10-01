package com.example.Skool.auth.entities;

import lombok.Data;

import java.security.Principal;

@Data
public class UserPrincipal implements Principal {
    private String name;
    private int id;
    public UserPrincipal(int id, String name) {
        this.name = name;
        this.id = id;
    }
    @Override
    public String getName() {
        return name;
    }
}
