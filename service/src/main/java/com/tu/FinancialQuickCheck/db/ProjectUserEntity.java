package com.tu.FinancialQuickCheck.db;

import com.tu.FinancialQuickCheck.Role;

import javax.persistence.*;
import java.util.Objects;

/**
 * The ProjectUserEntity-class represents the project user's database table, in which each class‘ attribute corresponds
 * to a row
 */
@Entity // This tells Hibernate to make a table out of this class
public class ProjectUserEntity {
    //TODO: (prio: medium) UserManagement - add link to projectEntity to be able to fetch all projects for one user

    /**
     * ID of project user (as composite primary key).
     */
    @EmbeddedId
    public ProjectUserId projectUserId;

    /**
     * Role of user in project which is admin, project manager or client.
     */
    @Column(name = "role")
    public Role role;

    /**
     * Constructor for class ProjectUserEntity.
     */
    public ProjectUserEntity(){}

}
