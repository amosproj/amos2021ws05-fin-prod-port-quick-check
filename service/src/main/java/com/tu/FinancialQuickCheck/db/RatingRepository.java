package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * JPA repository infrastructure scans classpath for RatingRepository interface, creates a Spring bean for it and
 * implements CRUD-methods.
 */
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    /**
     * This method returns ratings for their rating area (complexity or economic).
     *
     * @param ratingArea The rating area for which ratings should be returned.
     * @return The ratings for a rating area (complexity or economic).
     */
    Iterable<RatingEntity> findByRatingarea(RatingArea ratingArea);

}