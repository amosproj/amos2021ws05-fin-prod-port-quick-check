package com.tu.FinancialQuickCheck.db;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the ID of an user which is assigned to a specific project.
 */
@Embeddable
public class ProjectUserId implements Serializable {

    /**
     * Project to which a User is assigned to.
     */
    //TODO: (done - need review) rename ...id declarations
    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "project")
    private ProjectEntity project;

    /**
     * User which is assigned to a project and therefore has a ProjectUserID.
     */
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private UserEntity user;


    /**
     * No-argument Constructor for class ProjectUserID.
     */
    public ProjectUserId(){}

    /**
     * Constructor for class ProjectUserID.
     *
     * @param project Project to which a user is assgined to.
     * @param user The user which is assgined to a project.
     */
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

    /**
     * Compares two projectUserIDs based on the user and project.
     *
     * @param o projectUserID which has to be compared.
     * @return True when the two projectUserIDs are identical.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectUserId that = (ProjectUserId) o;
        return project.equals(that.project) && user.equals(that.user);
    }

    /**
     * Gives back the hashcode value of projectUserID.
     *
     * @return Hashcode value of projectUserID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(project, user);
    }
}
