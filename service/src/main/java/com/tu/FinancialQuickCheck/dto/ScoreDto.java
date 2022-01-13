package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;

/**
 *
 *
 */
public class ScoreDto {

    public Score score;
    public int count;

    public ScoreDto(){}

    public ScoreDto(Score score, int count)
    {
        this.score = score;
        this.count = count;
    }
}
