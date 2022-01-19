package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA repository infrastructure scans classpath for RatingRepository interface, creates a Spring bean for it and
 * implements CRUD-methods.
 */
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {


    List<RatingEntity> findByRatingarea(RatingArea ratingArea);

}
