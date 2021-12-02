package com.tu.FinancialQuickCheck.dto;

//import com.tu.FinancialQuickCheck.RatingArea;
//import com.tu.FinancialQuickCheck.Score;

import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    public int  productID;
    public String productName;
    public int productAreaID;
    public int projectID;
    public List<ProductRatingDto> ratings;
    public List<ProductDto> productVariations;

//    public Score economicRating;
//
//    public Score complexityRating;
//

    public ProductDto(){}

    public ProductDto(String name, int productAreaID){
        this.productName = name;
        this.productAreaID = productAreaID;
    }

    public ProductDto(int id, int projectID, int productAreaID){
        this. productID = id;
        this.projectID = projectID;
        this.productAreaID = productAreaID;
    }

    public ProductDto(int id, String name, int projectID, int productAreaID){
        this.productID = id;
        this.productName = name;
        this.projectID = projectID;
        this.productAreaID = productAreaID;
    }

    public ProductDto(String name, List<ProductRatingEntity> productRatingEntities)
    {
        this.productName = name;
        this.ratings = convertProductRatingEntities(productRatingEntities);
    }

//    public Product(int id, String name, List<ProductEntity> productEntities, List<RatingEntity> ratingEntities)
//    {
//        this.id = id;
//        this.name = name;
//        this.productVariations = new ArrayList<>();
//        for (ProductEntity p: productEntities) {
//            productVariations.add(new Product(p.id, p.name, p.ratingEntities));
//        }
//        this.economicRatingCriterions = new ArrayList<>();
//        this.complexityRatingCriterions = new ArrayList<>();
//        for (RatingEntity r: ratingEntities) {
//            if (r.area == RatingArea.ECONOMIC)
//                economicRatingCriterions.add(new Rating(r.id, r.criterion, r.score,r.criterionValue,r.comment));
//            else
//                complexityRatingCriterions.add(new Rating(r.id, r.criterion, r.score,r.criterionValue,r.comment));
//        }
//        economicRating = computeRating(economicRatingCriterions);
//        complexityRating = computeRating(complexityRatingCriterions);
//    }

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
            p.ratingID = entity.productRatingId.getRatingid().id;
            p.score = entity.score;
            p.answer = entity.answer;
            p.comment = entity.comment;
            tmp.add(p);
        }

        return tmp;
    }
}
