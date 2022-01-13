package com.tu.FinancialQuickCheck.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class ResultDto {

    public String productName;
    public List<ProductRatingDto> ratings;
    public List<ScoreDto> scores;

    public ResultDto(){}

    public ResultDto(String productName, List<ProductRatingDto> ratings, List<ScoreDto> scores)
    {
        this.productName = productName;
        this.ratings = ratings;
        this.scores = scores;
    }
}
