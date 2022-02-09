package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;

/**
 * The UserEntity-class represents the user's database table, in which each class‘ attribute corresponds
 * to a row
 */
@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {
    //TODO: (done --needs review) change primary key to id

    /**
     * ID of user (as primary key).
     */
    @Id
    public String id;

    /**
     * Chosen name of user.
     */
    @Column(name = "username")
    public String username;

    /**
     * Stored email address to user.
     */
    @Column(name = "email")
    public String email;

    /**
     * Chosen password for user.
     */
    @Column(name = "password")
    public String password;

    /**
     * Constructor for class UserEntity.
     */
    public UserEntity(){}

}
