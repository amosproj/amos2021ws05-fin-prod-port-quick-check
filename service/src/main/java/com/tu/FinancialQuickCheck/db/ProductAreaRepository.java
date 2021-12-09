package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called projectRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductAreaRepository extends JpaRepository<ProductAreaEntity, Integer> {

}
