package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.db.ProjectUserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * This class represents the project data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class ProjectDto {

    public int projectID;
    public UUID creatorID;
    public String projectName;
    public List<ProjectUserDto> members;
    public List<ProductAreaDto> productAreas;

    public ProjectDto() {}

    public ProjectDto(ProjectEntity project){
        this.projectID = project.id;
        this.projectName = project.name;
        this.creatorID = UUID.fromString(project.creatorID);
        this.members = convertProjectUserEntities(project.projectUserEntities);
        this.productAreas = new ListOfProductAreaDto(project).productAreas;
    }

    private List<ProjectUserDto> convertProjectUserEntities(List<ProjectUserEntity> projectUserEntities) {
        List<ProjectUserDto> members = new ArrayList<>();

        for (ProjectUserEntity member: projectUserEntities)
        {
            members.add(new ProjectUserDto(member));
        }
        return members;
    }
}
