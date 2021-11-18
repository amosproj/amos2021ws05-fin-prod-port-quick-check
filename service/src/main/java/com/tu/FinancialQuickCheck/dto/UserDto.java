package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;

import java.util.UUID;

public class UserDto {

    public UUID id;

    public String email;

    public String password;

    public Role role;

    public UserDto(){};

    public UserDto(String email)
    {
        this.email = email;
    }

    public UserDto(String email, Role role)
    {
        this.email= email;
        this.role = role;
    }

    public UserDto(UUID userID, String email)
    {
        this.id = userID;
        this.email=email;
    }

    public UserDto(UUID userID, String email, Role role)
    {
        this.id = userID;
        this.email=email;
        this.role = role;
    }

}
