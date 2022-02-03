package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.UserEntity;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * This class represents the user data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class UserDto {

    public UUID userID;
    public String userEmail;
    public String userName;
    public String password;

    public UserDto(){}


    public UserDto(UUID userID, String email, String username)
    {
        this.userID = userID;
        this.userEmail = email;
        this.userName = username;
    }

    public UserDto(UserEntity user)
    {
        this.userID = UUID.fromString(user.id);
        this.userEmail = user.email;
        this.userName = user.username;
    }


    public boolean validateEmail(String emailAddress){
        // regexPattern from RFC 5322 for Email Validation
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
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
