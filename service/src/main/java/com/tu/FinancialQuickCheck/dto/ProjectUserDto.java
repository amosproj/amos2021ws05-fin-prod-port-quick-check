package com.tu.FinancialQuickCheck.dto;

import java.util.UUID;

public class ProjectUserDto {

    public UUID userID;
    public int projectID;


    public ProjectUserDto(UUID userID, int projectID)
    {
        this.userID = UUID.randomUUID();
        this.projectID=projectID;
    }

}
