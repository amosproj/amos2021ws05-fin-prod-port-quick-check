package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called projectRepository


//TODO: (done - needs review) change to jpa
public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, ProjectUserId> {

    void deleteByProjectUserId_project(ProjectEntity projectEntity);
}
