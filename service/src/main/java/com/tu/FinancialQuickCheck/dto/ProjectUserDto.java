package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;
import com.tu.FinancialQuickCheck.db.ProjectUserEntity;

import java.util.UUID;
import java.util.regex.Pattern;

public class ProjectUserDto {

    public UUID userID;
    public String userName;
    public String userEmail;
    public Role role;


    public ProjectUserDto(){}

    public ProjectUserDto(String email, Role role)
    {
        this.userEmail = email;
        this.role = role;
    }


    public ProjectUserDto(ProjectUserEntity entity)
    {
        this.userID = UUID.fromString(entity.projectUserId.getUser().id);
        this.userEmail = entity.projectUserId.getUser().email;
        this.userName = entity.projectUserId.getUser().username;
        this.role = entity.role;
    }

    public boolean validateEmail(String emailAddress){
        // regexPattern from RFC 5322 for Email Validation
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}
