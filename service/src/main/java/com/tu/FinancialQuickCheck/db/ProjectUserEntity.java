package com.tu.FinancialQuickCheck.db;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity // This tells Hibernate to make a table out of this class
public class ProjectUserEntity {

    // TODO: get rid of id and replace with key consisting of projectID + userID
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "projectid")
    public int projectID;

    @Column(name = "userid")
    public UUID userID;

}