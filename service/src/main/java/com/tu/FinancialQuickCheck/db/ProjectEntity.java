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

    @Column(name = "creator_id")
    public int creator_id;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    public List<ProductEntity> productEntities;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id")
//    public List<UserEntity> userEntities;

    public ProjectEntity(){
    }

    public ProjectEntity(String name, int creatorID) {
        this.name = name;
        this.creator_id = creatorID;
    }

}