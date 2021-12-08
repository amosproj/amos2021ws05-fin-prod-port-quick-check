package com.tu.FinancialQuickCheck.db;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProjectUserId implements Serializable {

    @ManyToOne(targetEntity = ProjectEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "projectid")
    private ProjectEntity projectid;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private UserEntity userid;

    public ProjectUserId(){}

    public ProjectUserId(ProjectEntity project, UserEntity user){
        this.projectid = project;
        this.userid = user;
    }

    public ProjectEntity getProjectid() {
        return projectid;
    }

    public void setProjectid(ProjectEntity projectid) {
        this.projectid = projectid;
    }

    public UserEntity getUserid() {
        return userid;
    }

    public void setUserid(UserEntity userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectUserId that = (ProjectUserId) o;
        return projectid.equals(that.projectid) && userid.equals(that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectid, userid);
    }
}
