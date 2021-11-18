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
        // TODO: fix bug in UUID creation --> UUID.fromString needs as input parameter a string verion of a UUID
        // this.id = UUID.fromString(email);
        this.id = UUID.randomUUID();
        this.email=email;
        this.role = role;
    }

}
