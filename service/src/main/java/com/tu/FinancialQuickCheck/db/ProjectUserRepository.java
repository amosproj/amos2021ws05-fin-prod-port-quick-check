package com.tu.FinancialQuickCheck.db;


import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called projectRepository
// CRUD refers Create, Read, Update, Delete bbb


//TODO: change to jpa
public interface ProjectUserRepository extends CrudRepository<ProjectUserEntity, ProjectUserId> {

    long deleteByProjectUserId_projectid(ProjectEntity projectEntity);
}