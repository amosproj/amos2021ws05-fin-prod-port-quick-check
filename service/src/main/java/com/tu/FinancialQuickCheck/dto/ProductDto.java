package com.tu.FinancialQuickCheck.dto;

//import com.tu.FinancialQuickCheck.RatingArea;
//import com.tu.FinancialQuickCheck.Score;

import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
//import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    // TODO: add progressComplexity and progressEconomic to necessary constructors
    public int  productID;
    public String productName;
    public ProductAreaDto productArea;
    public int projectID;
    public int parentID;
    public int progressComplexity;
    public int progressEconomic;
    public List<ProductRatingDto> ratings;
    public List<ProductDto> productVariations;


    public ProductDto(){}

    public ProductDto(String name, ProductAreaDto productArea){
        this.productName = name;
        this.productArea = productArea;
    }

    public ProductDto(int id, int projectID, ProductAreaEntity productArea){
        this.productID = id;
        this.projectID = projectID;
        this.productArea = convertProductAreaEntity(productArea);
    }

    public ProductDto(int id, String name, int projectID, ProductAreaEntity productArea, ProductEntity parent){
        this.productID = id;
        this.productName = name;
        this.projectID = projectID;
        this.productArea = convertProductAreaEntity(productArea);
        this.parentID = convertParentEntity(parent);
    }

    public ProductDto(int id, String name, int projectID, ProductEntity parent){
        this.productID = id;
        this.productName = name;
        this.projectID = projectID;
        this.parentID = convertParentEntity(parent);
    }

    public ProductDto(String name, List<ProductRatingEntity> productRatingEntities)
    {
        this.productName = name;
        this.ratings = convertProductRatingEntities(productRatingEntities);
    }


//    private Score computeRating(List<Rating> values)
//    {
//        int sum = 0;
//        for (Rating r: values) {
//            sum += r.score.getValue();
//        }
//        return Score.valueOf(sum/values.size());
//    }


    private List<ProductRatingDto> convertProductRatingEntities(List<ProductRatingEntity> productRatingEntities) {
        List<ProductRatingDto> tmp = new ArrayList<>();

        for(ProductRatingEntity entity : productRatingEntities){
            ProductRatingDto p = new ProductRatingDto();
            p.ratingID = entity.productRatingId.getRating().id;
            p.score = entity.score;
            p.answer = entity.answer;
            p.comment = entity.comment;
            tmp.add(p);
        }

        return tmp;
    }

    private ProductAreaDto convertProductAreaEntity(ProductAreaEntity productAreaEntity) {
        return new ProductAreaDto(productAreaEntity.id, productAreaEntity.name, productAreaEntity.category);
    }

    //TODO: (done - review needed) change returned parent-id to 0 if no parent exist
    private int convertParentEntity(ProductEntity parentEntity) {
        if(parentEntity != null){
            return parentEntity.id;
        }else{
            return 0;
        }
    }
}
