package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, ProjectUserId> {

    void deleteByProjectUserId_project(ProjectEntity projectEntity);
}
