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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    public List<ProductEntity> productEntities;

    public ProjectEntity(){
    }

    public ProjectEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

}