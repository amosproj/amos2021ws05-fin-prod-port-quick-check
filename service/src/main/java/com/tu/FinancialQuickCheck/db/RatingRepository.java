package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.RatingArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA repository infrastructure scans classpath for RatingRepository interface, creates a Spring bean for it and
 * implements CRUD-methods.
 */
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    /**
     * Retrieves all rating entities, that belong to specified ratingArea, from db or the ones for the
     *
     * @param ratingArea The rating area is used in the WHERE-clausel of the sql query
     * @return A list of ratings if exist else empty list.
     */
    List<RatingEntity> findByRatingarea(RatingArea ratingArea);

}
