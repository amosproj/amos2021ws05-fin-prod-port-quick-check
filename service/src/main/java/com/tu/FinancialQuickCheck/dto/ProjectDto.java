package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProjectUserEntity;

import java.util.ArrayList;
import java.util.HashSet;
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


    public ProjectDto(int projectID, String projectName, UUID creator,
                      List<ProductEntity> productEntities, List<ProjectUserEntity> projectUserEntities){
        this.projectID = projectID;
        this.creatorID = creator;
        this.projectName = projectName;
        this.members = convertProjectUserEntities(projectUserEntities);
        this.productAreas = convertProductAreaEntities(productEntities);
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

    private List<UserDto> convertProjectUserEntities(List<ProjectUserEntity> projectUserEntities) {
        List<UserDto> members = new ArrayList<>();

        for (ProjectUserEntity member: projectUserEntities)
        {
            members.add(new UserDto(
                            UUID.fromString(member.projectUserId.getUser().id),
                            member.projectUserId.getUser().username,
                            member.projectUserId.getUser().email,
                            member.role)
                    );
        }
        return members;
    }

    private List<ProductAreaDto> convertProductAreaEntities(List<ProductEntity> productEntities) {
        //TODO: (prio: low) greift alle Produktdaten für project ab, es würde ausreichen nur die DUMMY Daten abzugreifen
        HashSet<ProductAreaDto> areas = new HashSet<>();

        for (ProductEntity product: productEntities)
        {
            areas.add(new ProductAreaDto(
                    product.productarea.id,
                    product.productarea.name,
                    product.productarea.category
            ));
        }
        return new ArrayList<>(areas);
    }
}
