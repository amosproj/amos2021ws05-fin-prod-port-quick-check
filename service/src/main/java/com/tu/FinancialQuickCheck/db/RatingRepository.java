package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {


    Iterable<RatingEntity> findByRatingarea(RatingArea ratingArea);

}