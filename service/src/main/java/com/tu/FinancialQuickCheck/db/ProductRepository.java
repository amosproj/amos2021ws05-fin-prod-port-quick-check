package com.tu.FinancialQuickCheck.db;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {


    Boolean existsByProjectidAndProductareaid(int projectID, int productAreaId);

}