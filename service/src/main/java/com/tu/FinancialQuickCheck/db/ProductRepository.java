package com.tu.FinancialQuickCheck.db;


import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    Boolean existsByProjectidAndProductareaid(int projectID, int productAreaId);

    Iterable<ProductEntity> findByProjectid(int projectID);

    Iterable<ProductEntity> findByProjectidAndProductareaid(int projectID, int productAreaId);

}