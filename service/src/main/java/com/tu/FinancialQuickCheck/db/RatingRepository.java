package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<RatingEntity, Integer> {


    Iterable<RatingEntity> findByRatingarea(RatingArea ratingArea);

}