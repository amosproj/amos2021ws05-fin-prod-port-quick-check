package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProjectEntity;
import com.tu.FinancialQuickCheck.db.ProjectUserEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


public class ProjectDto {
    public int projectID;
    public UUID creatorID;
    public String projectName;
    public List<ProjectUserDto> members;
    public List<ProductAreaDto> productAreas;
//    public List<Product> products;

    //necessary for mapping
    public ProjectDto() {}


    public ProjectDto(ProjectEntity project){
        this.projectID = project.id;
        this.projectName = project.name;
        this.creatorID = UUID.fromString(project.creatorID);
        this.members = convertProjectUserEntities(project.projectUserEntities);
        this.productAreas = new ListOfProductAreaDto(project).productAreas;
    }



//    public ProjectDto(int id, String name)
//    {
//        this.name = name;
//        this.id = id;
////        this.products = convertEntities(productEntity);
//    }

//    private List<Product> convertEntities(List<ProductEntity> productEntity) {
//        List<Product> products = new ArrayList<>();
//        for (ProductEntity p: productEntity)
//        {
////            if(p.productVariations != null)
////                products.add(new Product(p.id, p.name, p.productVariations, p.ratingEntities));
////            else
//            products.add(new Product(p.id, p.name, p.ratingEntities));
//        }
//        return products;
//    }

    private List<ProjectUserDto> convertProjectUserEntities(List<ProjectUserEntity> projectUserEntities) {
        List<ProjectUserDto> members = new ArrayList<>();

        for (ProjectUserEntity member: projectUserEntities)
        {
            members.add(new ProjectUserDto(member));
        }
        return members;
    }
}
