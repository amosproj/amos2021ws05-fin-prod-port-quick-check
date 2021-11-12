package com.tu.FinancialQuickCheck.dto;

//import java.util.ArrayList;
import java.util.List;


public class ProjectDto {

    public int id;
    public String name;
    public int creatorID;
    public Integer[] members;
    public Integer[] productAreas;
//    public List<Product> products;

    //necessary for mapping
    public ProjectDto() {}

    public ProjectDto(int projectID, String projectName, int creatorID, Integer[] members, Integer[] productAreas ){
        this.id = projectID;
        this.name = projectName;
        this.creatorID = creatorID;
        this.members = members;
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
//        for (ProductEntity p:productEntity)
//        {
//            if(p.productVariations != null)
//                products.add(new Product(p.id, p.name, p.productVariations, p.ratingEntities));
//            else
//                products.add(new Product(p.id, p.name, p.ratingEntities));
//        }
//        return products;
//    }
}