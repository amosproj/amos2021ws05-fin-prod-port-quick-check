package com.tu.FinancialQuickCheck.db;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int id;

    @Column(name = "project_id")
    public String project_id;

    @Column(name = "name")
    public String name;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    public List<ProductEntity> productVariations;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public List<RatingEntity> ratingEntities;
}
