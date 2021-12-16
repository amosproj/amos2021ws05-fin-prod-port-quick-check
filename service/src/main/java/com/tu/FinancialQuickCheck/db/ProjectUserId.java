package com.tu.FinancialQuickCheck.db;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProjectUserId implements Serializable {


    //TODO: (done - need review) rename ...id declarations
    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "project")
    private ProjectEntity project;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private UserEntity user;

    public ProjectUserId(){}

    public ProjectUserId(ProjectEntity project, UserEntity user){
        this.project = project;
        this.user = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectUserId that = (ProjectUserId) o;
        return project.equals(that.project) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, user);
    }
}
