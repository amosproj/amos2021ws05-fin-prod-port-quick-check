package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

/**
 * The ProjectEntity-class represents the project's database table, in which each class‘ attribute corresponds
 * to a row
 */
@Entity // This tells Hibernate to make a table out of this class
public class ProjectEntity {

    /**
     * ID of the project (as primary key).
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    /**
     * Name of the project. Usually identical with the name of the financial institution using the software.
     */
    @Column(name = "name")
    public String name;

    /**
     * ID of the person who initially created the project.
     */
    @Column(name = "creator")
    public String creatorID;

    /**
     * One project can include many products.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project")
    public List<ProductEntity> productEntities;

    /**
     * One project can include many project users.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project")
    public List<ProjectUserEntity> projectUserEntities;

    /**
     * Constructor for class ProjectEntity.
     */
    public ProjectEntity(){
    }

}