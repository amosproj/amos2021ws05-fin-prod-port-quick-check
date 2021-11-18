package com.tu.FinancialQuickCheck.db;


import org.springframework.data.repository.CrudRepository;



public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {


    Boolean existsByProjectidAndProductareaid(int projectID, int productAreaId);

    Iterable<ProductEntity> findByProjectid(int projectID);

    Iterable<ProductEntity> findByProjectidAndProductareaid(int projectID, int productAreaId);

}