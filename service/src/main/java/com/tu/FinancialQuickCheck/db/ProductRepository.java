package com.tu.FinancialQuickCheck.db;


import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    Boolean existsByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

    Iterable<ProductEntity> findByProject(ProjectEntity project);

    Iterable<ProductEntity> findByProjectAndProductarea(ProjectEntity project, ProductAreaEntity productArea);

}