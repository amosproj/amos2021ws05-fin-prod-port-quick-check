package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.Role;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(255)")
    public UUID id;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;

    @Column(name = "role", columnDefinition = "varchar(255)")
    public Role role;

    public UserEntity(){};

}