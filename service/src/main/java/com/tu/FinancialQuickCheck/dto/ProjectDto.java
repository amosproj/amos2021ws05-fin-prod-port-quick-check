package com.tu.FinancialQuickCheck.dto;

//import java.util.ArrayList;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProjectUserEntity;
import org.h2.command.dml.Set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


public class ProjectDto {

    public int projectID;
    public String projectName;
    public int creatorID;
    public HashSet<UUID> members;
    public HashSet<Integer> productAreas;
//    public List<Product> products;

    //necessary for mapping
    public ProjectDto() {}

    public ProjectDto(int projectID){
        this.projectID = projectID;
    }

    public ProjectDto(int projectID, String projectName, int creatorID,
                      List<ProductEntity> productEntity, List<ProjectUserEntity> projectUserEntities){
        this.projectID = projectID;
        this.projectName = projectName;
        this.creatorID = creatorID;
        this.members = convertProjectUserEntities(projectUserEntities);
        this.productAreas = convertProductAreaEntities(productEntity);
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

    private HashSet<Integer> convertProductAreaEntities(List<ProductEntity> productEntity) {
        HashSet<Integer> areas = new HashSet<>();
        for (ProductEntity p: productEntity)
        {
            areas.add(p.productareaid);
        }
        return areas;
    }

    private HashSet<UUID> convertProjectUserEntities(List<ProjectUserEntity> projectUserEntities) {
        HashSet<UUID> members = new HashSet<>();

        for (ProjectUserEntity member: projectUserEntities)
        {
            members.add(UUID.fromString(member.projectUserId.getUserid().id));
        }
        return members;
    }
}
