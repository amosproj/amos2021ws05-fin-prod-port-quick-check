package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the product data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class ProductDto {

    public int  productID;
    public String productName;
    public ProductAreaDto productArea;
    public int projectID;
    public int parentID;
    public float progressComplexity;
    public float progressEconomic;
    public List<ProductRatingDto> ratings;
    public List<ProductDto> productVariations;
    public String comment;
    public List<String> resources;


    public ProductDto(){}


    public ProductDto(String name, ProductAreaDto productArea){
        this.productName = name;
        this.productArea = productArea;
    }


    public ProductDto(int id, int projectID, ProductAreaEntity productArea){
        this.productID = id;
        this.projectID = projectID;
        this.productArea = new ProductAreaDto(productArea);
    }


    public ProductDto(int id, String name, int projectID, ProductAreaEntity productArea, ProductEntity parent){
        this.productID = id;
        this.productName = name;
        this.projectID = projectID;
        this.productArea = new ProductAreaDto(productArea);
        this.parentID = convertParentEntity(parent);
    }


    public ProductDto(int id, String name, int projectID, ProductEntity parent){
        this.productID = id;
        this.productName = name;
        this.projectID = projectID;
        this.parentID = convertParentEntity(parent);
    }

    // used for ProductRatings
    public ProductDto(ProductEntity product, List<ProductRatingEntity> productRatingEntities)
    {
        this.comment = product.comment;
        this.projectID = product.project.id;
        this.productID = product.id;
        this.productName = product.name;
        this.parentID = convertParentEntity(product.parentProduct);
        this.ratings = convertProductRatingEntities(productRatingEntities);
    }

    // used in ProductService
    public ProductDto(ProductEntity product, float[] progress){
        this.productID = product.id;
        this.productName = product.name;
        this.productArea = new ProductAreaDto(product.productarea);
        this.projectID = product.project.id;
        this.parentID = convertParentEntity(product.parentProduct);
        this.progressComplexity = progress[1];
        this.progressEconomic = progress[0];
        this.comment = product.comment;
        this.resources = new ArrayList<>();
    }


    private List<ProductRatingDto> convertProductRatingEntities(List<ProductRatingEntity> productRatingEntities) {
        List<ProductRatingDto> tmp = new ArrayList<>();

        for(ProductRatingEntity entity : productRatingEntities){
                tmp.add(new ProductRatingDto(entity));
        }
        return tmp;
    }

    private int convertParentEntity(ProductEntity parentEntity) {
        if(parentEntity != null){
            return parentEntity.id;
        }else{
            return 0;
        }
    }
}
