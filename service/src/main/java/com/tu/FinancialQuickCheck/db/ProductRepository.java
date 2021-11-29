package com.tu.FinancialQuickCheck.db;


import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    Boolean existsByProjectidAndProductareaid(ProjectEntity projectID, int productAreaId);

    Iterable<ProductEntity> findByProjectid(ProjectEntity projectID);

    Iterable<ProductEntity> findByProjectidAndProductareaid(ProjectEntity projectID, int productAreaId);

}