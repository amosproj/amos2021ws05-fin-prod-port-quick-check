package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {

    @Column(name = "id")
    public String id;

    @Column(name = "username")
    public String username;

    @Id
    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;


    public UserEntity(){}

}