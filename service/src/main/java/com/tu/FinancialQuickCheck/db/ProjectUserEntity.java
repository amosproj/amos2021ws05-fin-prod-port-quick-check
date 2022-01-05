package com.tu.FinancialQuickCheck.db;


import com.tu.FinancialQuickCheck.Role;

import javax.persistence.*;
import java.util.Objects;


@Entity // This tells Hibernate to make a table out of this class
public class ProjectUserEntity {
    //TODO: (prio: medium) UserManagement - add link to projectEntity to be able to fetch all projects for one user

    @EmbeddedId
    public ProjectUserId projectUserId;

    @Column(name = "role")
    public Role role;

    public ProjectUserEntity(){}

}