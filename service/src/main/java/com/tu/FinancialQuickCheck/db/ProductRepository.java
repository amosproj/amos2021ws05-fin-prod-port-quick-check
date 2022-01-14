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

}
