package com.tu.FinancialQuickCheck.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *  JPA repository infrastructure scans classpath for UserRepository interface, creates a Spring bean for it and
 *  implements CRUD-methods.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * Retrieves user entity based on attribute email.
     *
     * @param email The email address of a user, should be unique.
     * @return Optional of UserEntity
     */
    Optional<UserEntity> findByEmail(String email);

}