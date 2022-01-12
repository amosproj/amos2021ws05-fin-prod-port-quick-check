package com.tu.FinancialQuickCheck.dto;

/**
 * This class represents the small project data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class SmallProjectDto {

    public int projectID;
    public String projectName;

    public SmallProjectDto(int id, String name){
        this.projectID = id;
        this.projectName = name;
    }
}
