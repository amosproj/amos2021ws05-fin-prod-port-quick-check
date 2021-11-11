package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.Score;

public class Rating {

    public int id;

    public String criterion;

    public Score score;

    public String criterionValue;

    public String comment;

    public Rating(int id, String criterion, Score score, String criterionValue, String comment)
    {
        this.id = id;
        this.criterion = criterion;
        this.score = score;
        this.criterionValue = criterionValue;
        this.comment = comment;
    }
}
