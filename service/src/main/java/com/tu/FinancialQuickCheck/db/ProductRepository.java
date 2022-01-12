package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  JPA repository infrastructure scans classpath for ProductRepository interface, creates a Spring bean for it and
 *  implements CRUD-methods.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    /**
     * This method checks if a product already exists in a project.
     *
     * @param project The project in which a product might already exist.
     * @param productArea The product area in which a product might be included.
     * @return True if a product already exist in a project.
     */
    Boolean existsByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

    /**
     * This method gives back products included in a project.
     *
     * @param project The project for that products should be displayed.
     * @return Products for the project.
     */
    Iterable<ProductEntity> findByProject(ProjectEntity project);

    /**
     * This method gives back products included in a project based on project and product areas.
     *
     * @param project The project for that products should be displayed.
     * @param productArea The product area for that products should be displayed.
     * @return Products for a project and its product areas.
     */
    Iterable<ProductEntity> findByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

}