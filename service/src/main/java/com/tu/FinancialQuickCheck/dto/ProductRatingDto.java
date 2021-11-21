package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;


public class ProductRatingDto {

    public int id;
    public String answer;
    public String comment;
    public Score score;

    // Infos aus ProductEntity
//    public int productID;

    // Infos aus RatingEntity
    public int ratingID;

    public ProductRatingDto(String answer, String comment, Score score, int ratingID)
    {
        this.answer = answer;
        this.comment = comment;
        this.score = score;
        this.ratingID = ratingID;
    }
}
