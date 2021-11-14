package com.tu.FinancialQuickCheck.db;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called projectRepository
// CRUD refers Create, Read, Update, Delete bbb

public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer> {

//    @Query("select distinct f.product_area_id from ProjectEntity as f where f.product_area_id == productAreaId")
//    Integer getDistinctProduct(int productAreaId, int projectID);



}