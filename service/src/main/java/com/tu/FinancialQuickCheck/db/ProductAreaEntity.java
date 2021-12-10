package com.tu.FinancialQuickCheck.db;


import javax.persistence.*;
import java.util.List;


@Entity // This tells Hibernate to make a table out of this class
public class ProductAreaEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "category")
    public String category;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "productarea")
    public List<ProductEntity> productEntities;

}