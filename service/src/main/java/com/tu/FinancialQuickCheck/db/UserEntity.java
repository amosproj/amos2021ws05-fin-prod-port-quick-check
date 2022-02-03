package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;

/**
 * The UserEntity-class represents the user's database table, in which each class‘ attribute corresponds
 * to a column
 *
 * id: The unique identifier of a user
 * username: The name of a user, can be used for displaying purposes
 * email: The email address of a user, should also be unique to a user
 * password: The password used for login, functionality currently not supported by backend
 */
@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {

    @Id
    public String id;

    @Column(name = "username")
    public String username;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;

    public UserEntity(){}

}
