package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProjectUserEntity;

import java.util.ArrayList;
import java.util.List;

public class ListOfProjectUserDto {

    public List<ProjectUserDto> projectUsers;

//    public ListOfProjectUserDto(){}

    public ListOfProjectUserDto(Iterable<ProjectUserEntity> projectUserEntities){

        this.projectUsers = new ArrayList<>();

        for (ProjectUserEntity tmp : projectUserEntities) {
            this.projectUsers.add(new ProjectUserDto(tmp));
        }
    }

}
