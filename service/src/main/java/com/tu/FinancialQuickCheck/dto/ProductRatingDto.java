package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;
import com.tu.FinancialQuickCheck.db.RatingEntity;

/**
 * This class represents the product rating data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class ProductRatingDto {

    public int productID;
    public int ratingID;
    public RatingDto rating;

    public String answer;
    public String comment;
    public Score score;

    public ProductRatingDto(){}

    public ProductRatingDto(String answer, String comment, Score score, int ratingID)
    {
        this.ratingID = ratingID;
        this.answer = answer;
        this.comment = comment;
        this.score = score;
    }

    public ProductRatingDto(String answer, String comment, Score score, RatingEntity rating)
    {
        convertRatingEntity(rating);
        this.answer = answer;
        this.comment = comment;
        this.score = score;
    }

    private void convertRatingEntity(RatingEntity ratingEntity)
    {
        this.rating = new RatingDto();
        rating.id = ratingEntity.id;
        rating.criterion = ratingEntity.criterion;
        rating.category = ratingEntity.category;
        rating.ratingArea = ratingEntity.ratingarea;
    }
}
