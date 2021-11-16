package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.Role;

import javax.persistence.*;
import java.util.UUID;

@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public UUID id;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;

    @Column(name = "role")
    public Role role;



}