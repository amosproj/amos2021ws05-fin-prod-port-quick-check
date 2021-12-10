package com.tu.FinancialQuickCheck.db;


import com.tu.FinancialQuickCheck.Role;

import javax.persistence.*;
import java.util.Objects;


@Entity // This tells Hibernate to make a table out of this class
public class ProjectUserEntity {

    @EmbeddedId
    public ProjectUserId projectUserId;

    @Column(name = "role")
    public Role role;

    public ProjectUserEntity(){}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectUserEntity that = (ProjectUserEntity) o;
        return projectUserId.equals(that.projectUserId) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectUserId, role);
    }
}