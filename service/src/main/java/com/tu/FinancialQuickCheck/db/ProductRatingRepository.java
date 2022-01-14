package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * JPA repository infrastructure scans classpath for ProductRatingRepository interface, creates a Spring bean for it and
 * implements CRUD-methods.
 */
public interface ProductRatingRepository extends JpaRepository<ProductRatingEntity, ProductRatingId> {

    // TODO: was wird zurück gegeben wenn projectID nicht existiert? --> leere Liste
    // TODO: what is returned of ratings do not exist but ids exist?

    @Query("SELECT distinct pr from ProductRatingEntity as pr " +
            "join ProductEntity as p on pr.productRatingId.product = p " +
            "join RatingEntity r on r = pr.productRatingId.rating " +
            "where pr.productRatingId.product.project.id = :projectId and (pr.productRatingId.rating.id = 4 " +
            "or pr.productRatingId.rating.id = 5 or pr.productRatingId.rating.id = 10 " +
            "or pr.productRatingId.rating.id = 9)")
    List<ProductRatingEntity> getResultsByProject(@Param("projectId") int projectId);


    @Query("SELECT distinct pr from ProductRatingEntity as pr " +
            "join ProductEntity as p on pr.productRatingId.product = p " +
            "join RatingEntity r on r = pr.productRatingId.rating " +
            "where pr.productRatingId.product.project.id = :projectId " +
            "and pr.productRatingId.product.productarea.id = :productAreaId " +
            "and (pr.productRatingId.rating.id = 4 or pr.productRatingId.rating.id = 5 " +
            "or pr.productRatingId.rating.id = 10 or pr.productRatingId.rating.id = 9)")
    List<ProductRatingEntity> getResultsByProjectAndProductArea(@Param("projectId") int projectId,
                                                                @Param("productAreaId") int productAreaId);

}