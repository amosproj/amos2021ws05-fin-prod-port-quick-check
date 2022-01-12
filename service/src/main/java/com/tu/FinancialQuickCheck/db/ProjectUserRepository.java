package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository infrastructure scans classpath for ProjectUserRepository interface, creates a Spring bean for it and
 * implements CRUD-methods.
 */
//TODO: (done - needs review) change to jpa
public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, ProjectUserId> {

    /**
     * This method unassigns users from project.
     *
     * @param projectEntity The project from which a user should be unassigned.
     * @return Confirmation that user has been unassigned from project.
     */
    long deleteByProjectUserId_project(ProjectEntity projectEntity);
}