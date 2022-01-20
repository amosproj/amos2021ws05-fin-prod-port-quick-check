package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreDto scoreDto = (ScoreDto) o;
        return score == scoreDto.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
