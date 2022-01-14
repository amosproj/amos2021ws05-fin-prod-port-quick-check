package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 *
 */
public class ResultDto {

    public int productID;
    public String productName;
    public List<ProductRatingDto> ratings;
    public ScoreDto[] scores;

    public ResultDto(){
        this.ratings = new ArrayList<>();
        this.scores = new ScoreDto[3];
        for(int i = 0; i < scores.length; i++){
            scores[i] = new ScoreDto(Score.valueOf(i + 1), 0);
        }
    }

    public ResultDto(int productID, String productName, List<ProductRatingDto> ratings, ScoreDto[] scores)
    {
        this.productID = productID;
        this.productName = productName;
        this.ratings = ratings;
        this.scores = scores;
    }

    public void updateProductInfos(int productID, String productName){
        this.productID = productID;
        this.productName = productName;
    }
}
