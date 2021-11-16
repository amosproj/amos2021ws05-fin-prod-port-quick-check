package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;

import java.util.UUID;

public class UserDto {

    public UUID id;

    public String email;

    public String password;

    public Role role;

    public UserDto(String email, Role role)
    {
        this.id = UUID.fromString(email);
        this.email=email;
        this.role = role;
    }

}
