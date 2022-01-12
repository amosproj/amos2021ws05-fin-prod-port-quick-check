package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.RatingArea;

/**
 * This class represents the rating data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 */
public class RatingDto {

    public int id;

    public String criterion;

    public String category;

    public RatingArea ratingArea;

    public RatingDto(){}

    public RatingDto(int id, String criterion, String category, RatingArea ratingArea)
    {
        this.id = id;
        this.criterion = criterion;
        this.category = category;
        this.ratingArea = ratingArea;
    }
}
