package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    /**
     * Checks if product entities for project entity and product area entity in db exist.
     *
     * @param project The project entity for which products should exist
     * @param productArea The productArea entity for which products should exist
     * @return True if at least one product entity exists
     */
    Boolean existsByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

    /**
     * Retrieves all product entities from db for project entity.
     *
     * @param project The project entity for which products should be retrieved
     * @return List of product entities
     */
    List<ProductEntity> findByProject(ProjectEntity project);

    /**
     * Retrieves all product entities from db for project entity and product area entity.
     *
     * @param project The project entity for which products should be retrieved
     * @param productArea The productArea entity for which products should be retrieved
     * @return Iterable of product entities
     */
    Iterable<ProductEntity> findByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

}
