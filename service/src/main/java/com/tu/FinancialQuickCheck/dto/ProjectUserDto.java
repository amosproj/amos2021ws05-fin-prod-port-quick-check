package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;

import java.util.UUID;

public class ProjectUserDto {

    public UUID userID;
    public int projectID;
    public String userName;
    public String userEmail;
    public Role role;

    public ProjectUserDto(){}

    public ProjectUserDto(UUID userID, Role role, String email, int projectID, String username)
    {
        this.userID = userID;
        this.projectID = projectID;
        this.userName = username;
        this.userEmail = email;
        this.role = role;
    }

}
