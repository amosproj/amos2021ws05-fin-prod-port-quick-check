package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.db.ProductRatingEntity;
import com.tu.FinancialQuickCheck.db.RatingEntity;

/**
 * This class represents the product rating data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class ProductRatingDto {

    public int ratingID;
    public RatingDto rating;

    public String answer;
    public String comment;
    public Score score;

    public ProductRatingDto(){}

    public ProductRatingDto(ProductRatingEntity entity)
    {
        this.ratingID = entity.productRatingId.getRating().id;
        this.rating = new RatingDto(entity.productRatingId.getRating());
        this.answer = entity.answer;
        this.comment = entity.comment;
        this.score = entity.score;
    }

    public ProductRatingDto(String answer, Score score, RatingEntity rating)
    {
        this.rating = new RatingDto(rating);
        this.answer = answer;
        this.score = score;
    }
}
