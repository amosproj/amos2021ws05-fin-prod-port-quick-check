package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;


public class ProductRatingDto {

    public int productID;
    public int ratingID;

    public String answer;
    public String comment;
    public Score score;

    public ProductRatingDto(){}

    public ProductRatingDto(String answer, String comment, Score score, int ratingID)
    {
        this.answer = answer;
        this.comment = comment;
        this.score = score;
        this.ratingID = ratingID;
    }
}
