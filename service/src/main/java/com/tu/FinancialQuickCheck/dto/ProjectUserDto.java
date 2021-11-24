package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Role;

import java.util.UUID;

public class ProjectUserDto {

    public UUID userID;
    public int projectID;
    public Role role;


    public ProjectUserDto(UUID userID, int projectID, Role role)
    {
        this.userID = UUID.randomUUID();
        this.projectID=projectID;
        this.role = role;
    }

}
