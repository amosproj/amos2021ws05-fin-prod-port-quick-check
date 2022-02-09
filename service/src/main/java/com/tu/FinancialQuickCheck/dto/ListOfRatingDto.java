package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.db.RatingEntity;

import java.util.ArrayList;
import java.util.List;


public class ListOfRatingDto {

    public List<RatingDto> ratings;

    /**
     * Class Constructor specfying list of ratings with id, criterion, category and ratingArea through ratingEntities
     */
    public ListOfRatingDto(List<RatingEntity> ratingEntities)
    {
        this.ratings = new ArrayList<>();

        for(RatingEntity tmp : ratingEntities){
            ratings.add(new RatingDto(tmp));
        }
    }
}
