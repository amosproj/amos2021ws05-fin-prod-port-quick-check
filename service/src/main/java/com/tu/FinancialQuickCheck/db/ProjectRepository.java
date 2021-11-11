package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called projectRepository
// CRUD refers Create, Read, Update, Delete

public interface ProjectRepository extends CrudRepository<ProjectEntity, Integer> {

    @Query("select new ProjectEntity(f.id, f.name) from ProjectEntity as f")
    List<ProjectEntity> getProjects();

}