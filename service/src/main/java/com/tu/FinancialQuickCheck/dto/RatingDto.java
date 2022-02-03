package com.tu.FinancialQuickCheck.dto;

import com.tu.FinancialQuickCheck.RatingArea;
import com.tu.FinancialQuickCheck.db.RatingEntity;

/**
 * This class represents the rating data transfer object, which is used for reducing the number of multiple
 * method calls into a single one
 *
 * id: unique identifier of rating entity
 * criterion: The criterion represents either a question of the complexity evaluation
 *    or a criteria of the economic evaliation (e.g. margin, credit volume,...).
 * category: The category groups different criterions and a category represents for example
 *    a complexity driver (e.g. regulatory, payment terms, documentation,..).
 * ratingarea: The ratingarea groups different ratings entities and
 *    a RatingArea represents either the economic or complexity evaluation of a product
 */
public class RatingDto {

    public int id;
    public String criterion;
    public String category;
    public RatingArea ratingArea;

    public RatingDto(){}

    /**
     * Class Constructor specfying rating with id, criterion, category and ratingArea through ratingEntity
     */
    public RatingDto(RatingEntity ratingEntity)
    {
        this.id = ratingEntity.id;
        this.criterion = ratingEntity.criterion;
        this.category = ratingEntity.category;
        this.ratingArea = ratingEntity.ratingarea;
    }
}
