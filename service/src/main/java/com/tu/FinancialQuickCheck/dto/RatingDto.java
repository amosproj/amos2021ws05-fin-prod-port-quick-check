package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.RatingEntity;

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

    public RatingDto(RatingEntity ratingEntity)
    {
        this.id = ratingEntity.id;
        this.criterion = ratingEntity.criterion;
        this.category = ratingEntity.category;
        this.ratingArea = ratingEntity.ratingarea;
    }
}
