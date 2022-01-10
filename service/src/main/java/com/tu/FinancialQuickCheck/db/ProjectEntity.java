package com.tu.FinancialQuickCheck.db;


import javax.persistence.*;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "creator")
    public String creatorID;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project")
    public List<ProductEntity> productEntities;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project")
    public List<ProjectUserEntity> projectUserEntities;

    public ProjectEntity(){
    }

}