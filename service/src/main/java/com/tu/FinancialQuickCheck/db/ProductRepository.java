package com.tu.FinancialQuickCheck.db;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Boolean existsByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

    List<ProductEntity> findByProject(ProjectEntity project);

    Iterable<ProductEntity> findByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

}