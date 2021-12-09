package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;

import java.util.Objects;
import java.util.UUID;

public class UserDto {

    public String userEmail;
    public String userName;
    public UUID userID;
    public Role role;
    public String password;


    public UserDto(){}

    public UserDto(String email)
    {
        this.userEmail = email;
    }

    public UserDto(String email, Role role)
    {
        this.userEmail = email;
        this.role = role;
    }

    public UserDto(UUID userID, String email, String username)
    {
        this.userID = userID;
        this.userEmail = email;
        this.userName = username;
    }

    public UserDto(UUID userID, String email, Role role)
    {
        this.userID = userID;
        this.userEmail =email;
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return userEmail.equals(userDto.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail);
    }
}
