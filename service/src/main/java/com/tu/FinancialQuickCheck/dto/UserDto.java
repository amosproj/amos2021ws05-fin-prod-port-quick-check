package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.UserEntity;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * This class represents the user data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 *
 * id: The unique identifier of a user
 * userEmail: The email address of a user, should also be unique to a user
 * userName: The name of a user, can be used for displaying purposes
 * password: The password used for login, functionality currently not supported by backend
 */
public class UserDto {

    public UUID userID;
    public String userEmail;
    public String userName;
    public String password;

    public UserDto(){}

    /**
     * Class Constructor specfying userID, userEmail and userName.
     */
    public UserDto(UUID userID, String email, String username)
    {
        this.userID = userID;
        this.userEmail = email;
        this.userName = username;
    }

    /**
     * Class Constructor specfying userID, userEmail and userName through userEntity
     */
    public UserDto(UserEntity user)
    {
        this.userID = UUID.fromString(user.id);
        this.userEmail = user.email;
        this.userName = user.username;
    }

    /**
     * Validates an email address according to RFC 5322.
     */
    public boolean validateEmail(String emailAddress){
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
