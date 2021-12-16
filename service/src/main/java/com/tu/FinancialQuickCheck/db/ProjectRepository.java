package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called projectRepository

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {


}