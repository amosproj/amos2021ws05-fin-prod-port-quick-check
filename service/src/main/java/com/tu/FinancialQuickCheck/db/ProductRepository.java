package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Boolean existsByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

    List<ProductEntity> findByProject(ProjectEntity project);

    /**
     * This method gives back products included in a project based on project and product areas.
     *
     * @param project The project for that products should be displayed.
     * @param productArea The product area for that products should be displayed.
     * @return Products for a project and its product areas.
     */
    Iterable<ProductEntity> findByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

    // TODO: was wird zurück gegeben wenn projectID nicht existiert? leere Liste(?)
    @Query("SELECT p from ProductEntity as p " +
            "join ProductRatingEntity as pr on p = pr.productRatingId.product " +
            "join RatingEntity r on r = pr.productRatingId.rating " +
            "where p.project.id = :projectId and (r.id = 4 or r.id = 5 or r.id = 9 or r.id = 10)")
    List<ProductEntity> getResultsByProject(@Param("projectId") int projectId);

    // TODO: was wird zurück gegeben wenn projectID und/oder productAreaID nicht existiert? leere Liste(?)
    @Query("SELECT p from ProductEntity as p " +
            "join ProductRatingEntity as pr on p = pr.productRatingId.product " +
            "join RatingEntity r on r = pr.productRatingId.rating " +
            "where p.project.id = :projectId and p.productarea.id = :productAreaId " +
            "and (r.id = 4 or r.id = 5 or r.id = 9 or r.id = 10)")
    List<ProductEntity> getResultsByProjectAndProductArea(@Param("projectId") int projectId,
                                                           @Param("productAreaId") int productAreaId);
}
