package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "projectid")
    public int projectid;

    @Column(name = "productareaid")
    public int productareaid;


//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    public int parentID;
//    public List<ProductEntity> productVariations;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "productid", insertable = false, updatable = false)
    public List<ProductRatingEntity> productRatingEntities;
}
