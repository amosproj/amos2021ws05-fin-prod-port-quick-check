package com.tu.FinancialQuickCheck.dto;

//import com.tu.FinancialQuickCheck.RatingArea;
//import com.tu.FinancialQuickCheck.Score;

import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.db.ProductAreaEntity;
import com.tu.FinancialQuickCheck.db.ProductEntity;
import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
import com.tu.FinancialQuickCheck.db.RatingEntity;
//import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    // TODO: (done - needs review) add progressComplexity and progressEconomic to necessary constructors
    // TODO: (done - needs review) add overallEconomicRating to necessary constructors
    // TODO: (done - needs review) add comment to necessary constructors
    // TODO: (done - needs review) add List of resources to necessary constructors
    public int  productID;
    public String productName;
    public ProductAreaDto productArea;
    public int projectID;
    public int parentID;
    public int progressComplexity;
    public int progressEconomic;
    public Boolean overallEconomicRating;
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

    public ProductDto(ProductEntity product, List<ProductRatingEntity> productRatingEntities, Boolean getOrPostPut)
    {
        this.productName = product.name;
        this.overallEconomicRating = product.overallEconomicRating;
        this.ratings = convertProductRatingEntities(productRatingEntities, getOrPostPut);
    }

    public ProductDto(ProductEntity product){
        this.productID = product.id;
        this.productName = product.name;
        this.productArea = convertProductAreaEntity(product.productarea);
        this.projectID = product.project.id;
        this.parentID = convertParentEntity(product.parentProduct);
        //TODO: (prio: medium) replace 42 values with calculation for progress bars
        this.progressComplexity = 42;
        this.progressEconomic = 42;
        this.comment = product.comment;
        this.resources = new ArrayList<>();
    }


//    private Score computeRating(List<Rating> values)
//    {
//        int sum = 0;
//        for (Rating r: values) {
//            sum += r.score.getValue();
//        }
//        return Score.valueOf(sum/values.size());
//    }


    private List<ProductRatingDto> convertProductRatingEntities(List<ProductRatingEntity> productRatingEntities,
                                                                Boolean getOrPostPut) {
        List<ProductRatingDto> tmp = new ArrayList<>();

        if(getOrPostPut){
            for(ProductRatingEntity entity : productRatingEntities){
                tmp.add(new ProductRatingDto(
                            entity.answer,
                            entity.comment,
                            entity.score,
                            entity.productRatingId.getRating()));
            }
        }else{
            for(ProductRatingEntity entity : productRatingEntities){
                tmp.add(new ProductRatingDto(
                        entity.answer,
                        entity.comment,
                        entity.score,
                        entity.productRatingId.getRating().id));
            }
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
