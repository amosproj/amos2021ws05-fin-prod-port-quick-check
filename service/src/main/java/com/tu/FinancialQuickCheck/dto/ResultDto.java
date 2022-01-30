package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;

import java.sql.Array;
import java.util.*;

/**
 *
 *
 */
public class ResultDto {

    public int productID;
    public String productName;
    public List<ProductRatingDto> ratings;
    public ScoreDto[] scores;
    public int[] counts;

    public ResultDto(){
        this.ratings = new ArrayList<>();
        this.scores = new ScoreDto[3];
        this.counts = new int[3];
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

    public void setCounts(){
        for(ScoreDto score: this.scores){
            System.out.println(score.score + " " +  score.count);
            this.counts[score.score.getValue()-1] = score.count;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDto resultDto = (ResultDto) o;
        return productID == resultDto.productID && Objects.equals(productName, resultDto.productName) && Objects.equals(ratings, resultDto.ratings) && Arrays.equals(scores, resultDto.scores);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(productID, productName, ratings);
        result = 31 * result + Arrays.hashCode(scores);
        return result;
    }
}
