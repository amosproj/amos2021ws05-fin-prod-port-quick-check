package com.tu.FinancialQuickCheck.db;


import com.tu.FinancialQuickCheck.Role;

import javax.persistence.*;


@Entity // This tells Hibernate to make a table out of this class
public class ProjectUserEntity {

    @EmbeddedId
    public ProjectUserId projectUserId;

    @Column(name = "role")
    public Role role;

    public ProjectUserEntity(){}

}