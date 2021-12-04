package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProjectUserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ProjectDto {
    public int projectID;
    public UUID creatorID;
    public String projectName;
    public List<UserDto> members;
    public List<ProductAreaDto> productAreas;
//    public List<Product> products;

    //necessary for mapping
    public ProjectDto() {}

    public ProjectDto(UUID creatorID, String projectName, List<Integer> productAreas){
        this.creatorID = creatorID;
        this.projectName = projectName;
        this.productAreas = convertProductAreaSet(productAreas);
    }

    public ProjectDto(int projectID, String projectName, UUID creatorID, List<ProductAreaDto> productAreas){
        this.projectID = projectID;
        this.projectName = projectName;
        this.creatorID = creatorID;
        this.productAreas = productAreas;
    }

    public ProjectDto(int projectID, String projectName, UUID creatorID,
                      List<ProductAreaDto> productAreas, List<ProjectUserEntity> projectUserEntities){
        this.projectID = projectID;
        this.creatorID = creatorID;
        this.projectName = projectName;
        this.members = convertProjectUserEntities(projectUserEntities);
        this.productAreas = productAreas;
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

    private List<ProductAreaDto> convertProductAreaSet(List<Integer> productAreas) {
        List<ProductAreaDto> areas = new ArrayList<>();
        for (Integer productAreaId: productAreas)
        {
            areas.add(new ProductAreaDto(productAreaId));
        }
        return areas;
    }

//    private List<ProductAreaDto> convertProductAreaEntities(List<ProductEntity> productEntities) {
//        //TODO: lässt doppelte Werte zu, d.h. Datentyp anpassen
//        List<ProductAreaDto> areas = new ArrayList<>();
//        for (ProductEntity p: productEntities)
//        {
////            System.out.println(p.product_id + ": " + p.productareaid);
//            ProductAreaEntity tmp = new ProductAreaEntity();
//            tmp.id = p.productarea.id;
//            tmp.name = p.productarea.name;
//            tmp.category = p.productarea.category;
//
//            areas.add(new ProductAreaDto(
//                    p.productarea.id,
//                    p.productarea.name,
//                    p.productarea.category
//            ));
//        }
//        return areas;
//    }

    private List<UserDto> convertProjectUserEntities(List<ProjectUserEntity> projectUserEntities) {
        List<UserDto> members = new ArrayList<>();

        for (ProjectUserEntity member: projectUserEntities)
        {
            members.add(new UserDto(
                            UUID.fromString(member.projectUserId.getUserid().id),
                            member.projectUserId.getUserid().email,
                            member.role)
                    );
        }
        return members;
    }
}
