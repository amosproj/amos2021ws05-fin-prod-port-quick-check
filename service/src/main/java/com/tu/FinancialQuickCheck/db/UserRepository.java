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
     * This method returns the user entity belonging to an email address.
     *
     * @param email Email address for which a user entity should be returned.
     * @return User entity for a email address.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * This method checks if a user exists by their email address.
     *
     * @param email Email Address for which the existence of user should be checked.
     * @return True if the user exists which is checked by their email address.
     */
    Boolean existsByEmail(String email);

}