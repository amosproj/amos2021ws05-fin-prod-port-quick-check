package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository infrastructure scans classpath for ProductRatingRepository interface, creates a Spring bean for it and
 * implements CRUD-methods.
 */
public interface ProductRatingRepository extends JpaRepository<ProductRatingEntity, ProductRatingId> {

}